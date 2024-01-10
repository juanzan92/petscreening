package com.example.demo.resolver;

import com.example.demo.entities.Pet;
import com.example.demo.entities.PetEligibility;
import com.example.demo.entities.PetSearchCriteria;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.PetRepository;
import com.example.demo.specification.PetSpecification;
import com.example.demo.validator.EligibilityValidation;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PetResolver implements GraphQLQueryResolver {

    private final PetRepository petRepository;
    private final List<EligibilityValidation> eligibilityValidations;

    public PetResolver(PetRepository petRepository, List<EligibilityValidation> eligibilityValidations) {
        this.petRepository = petRepository;
        this.eligibilityValidations = eligibilityValidations;
    }

    public List<Pet> search(PetSearchCriteria criteria) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        var petSpecification = PetSpecification.buildSpecification(criteria);

        return petRepository.findAll(petSpecification, sort);
    }

    public PetEligibility checkPetViability(String petId) {
        var pet = petRepository.findByPId(petId)
                .orElseThrow(() -> NotFoundException.getException(petId));

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
}
