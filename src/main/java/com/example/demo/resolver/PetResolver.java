package com.example.demo.resolver;

import com.example.demo.entities.Pet;
import com.example.demo.entities.PetEligibility;
import com.example.demo.entities.input.PetInput;
import com.example.demo.entities.input.PetSearchCriteria;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.specification.PetSpecification;
import com.example.demo.validator.EligibilityValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.entities.Owner.buildOwnerFromInput;
import static com.example.demo.entities.Pet.buildPetFromInput;

@Slf4j
@Component
public class PetResolver {

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

    public Pet createPet(PetInput petInput) {
        log.info("PetResolver#addPet - name={} weight={} vaccinated={} breed={} trainingLevel={} ownerId={}",
                petInput.getName(),
                petInput.getWeight(),
                petInput.getVaccinated(),
                petInput.getBreed(),
                petInput.getTrainingLevel(),
                petInput.getOwnerInput().getPId());

        var owner = ownerRepository.findByPId(petInput.getOwnerInput().getPId())
                .orElse(buildOwnerFromInput(petInput.getOwnerInput()));

        //how to validate not insert the same pet twice ?

        var pet = buildPetFromInput(petInput, owner);

        owner.getPets().add(pet);
        return petRepository.save(pet);
    }
}
