package com.example.demo.resolver;

import com.example.demo.entities.Pet;
import com.example.demo.entities.PetEligibility;
import com.example.demo.entities.PetSearchCriteria;
import com.example.demo.entities.enums.Breed;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.specification.PetSpecification;
import com.example.demo.validator.EligibilityValidation;
import com.github.ksuid.Ksuid;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PetResolver implements GraphQLQueryResolver {

    public static final String PET_PREFIX = "pet-";
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final List<EligibilityValidation> eligibilityValidations;

    public PetResolver(PetRepository petRepository, OwnerRepository ownerRepository, List<EligibilityValidation> eligibilityValidations) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.eligibilityValidations = eligibilityValidations;
    }

    public List<Pet> search(PetSearchCriteria criteria) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        var petSpecification = PetSpecification.buildSpecification(criteria);

        return petRepository.findAll(petSpecification, sort);
    }

    public PetEligibility checkPetViability(String petId) {
        var pet = petRepository.findByPId(petId)
                .orElseThrow(() -> NotFoundException.getException("pet", petId));

        Map<String, Boolean> validationResultMap = new HashMap<>();

        eligibilityValidations.forEach(validator -> {
            var validationResult = false;
            try {
                validator.validate(pet);
                validationResult = true;
            } catch (Exception e) {
                //Do nothing but maybe to add to ErrorList and displayed which one fails.
            } finally {
                validationResultMap.put(validator.getClass().getCanonicalName(), validationResult);
            }
        });

        return PetEligibility.builder()
                .isValidPet(validationResultMap.values().stream().allMatch(Boolean::booleanValue))
                .petId(petId)
                .build();
    }

    public Pet createPet(String name, Float weight, Boolean vaccinated, String breed, int trainingLevel, String ownerId) {
        var owner = ownerRepository.findByPId(ownerId)
                .orElseThrow(() -> NotFoundException.getException("pet", ownerId));

        //how to validate not insert the same pet twice ?

        var pet = Pet.builder()
                .vaccinationStatus(vaccinated)
                .birthdate(Date.from(Instant.now()))
                .pId(getNewPId())
                .name(name)
                .weight(weight)
                .breed(Breed.valueOf(breed))
                .trainingLevel(trainingLevel)
                .owner(owner)
                .build();

        return petRepository.save(pet);
    }

    private String getNewPId() {
        return PET_PREFIX + Ksuid.newKsuid();
    }
}
