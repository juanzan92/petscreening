package com.example.demo.specification;

import com.example.demo.entities.Pet;
import com.example.demo.entities.input.PetSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecification {
    private PetSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Pet> buildSpecification(PetSearchCriteria criteria) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (criteria.getBreed() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("breed"), criteria.getBreed()));
            }
            if (criteria.getTrainingLevel() > 0) {
                predicate = builder.and(predicate, builder.equal(root.get("trainingLevel"), criteria.getTrainingLevel()));
            }
            if (Boolean.TRUE.equals(criteria.getVaccinationStatus())) {
                predicate = builder.and(predicate, builder.equal(root.get("vaccinationStatus"), criteria.getVaccinationStatus()));
            }
            if (criteria.getWeight() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("weight"), criteria.getWeight()));
            }

            return predicate;
        };
    }

}
