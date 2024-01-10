package com.example.demo.validator;

import com.example.demo.entities.Pet;
import com.example.demo.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TrainingLevelValidator implements EligibilityValidation {
    public static final int FLOOR_LEVEL = 3;

    @Override
    public void validate(Pet input) {
        if ( input.getTrainingLevel() <= FLOOR_LEVEL) {
            ValidationException.getException("Training Level too low");
        }
    }
}
