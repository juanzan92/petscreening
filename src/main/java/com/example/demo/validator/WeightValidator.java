package com.example.demo.validator;

import com.example.demo.entities.Pet;
import com.example.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class WeightValidator implements EligibilityValidation {
    public static final Float WEIGHT_LIMIT = 25f;

    @Override
    public void validate(Pet input) {
        if (!(input.getWeight() <= WEIGHT_LIMIT)) {
            ValidationException.getException("Weight limit not met");
        }
    }
}
