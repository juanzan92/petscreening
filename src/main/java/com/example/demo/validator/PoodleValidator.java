package com.example.demo.validator;

import com.example.demo.entities.Pet;
import com.example.demo.entities.enums.Breed;
import com.example.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PoodleValidator implements EligibilityValidation {
    @Override
    public void validate(Pet input) {
        if (Breed.POODLE.equals(input.getBreed())) {
            ValidationException.getException("Poodle is not allowed");
        }
    }
}
