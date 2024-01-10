package com.example.demo.validator;

import com.example.demo.entities.Pet;
import com.example.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class VaccinationValidator implements EligibilityValidation {

    @Override
    public void validate(Pet input) {
        if (Boolean.FALSE.equals(input.getVaccinationStatus())) {
            ValidationException.getException("vaccination");
        }
    }
}
