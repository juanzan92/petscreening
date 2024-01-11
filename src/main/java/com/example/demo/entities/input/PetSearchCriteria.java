package com.example.demo.entities.input;

import com.example.demo.entities.enums.Breed;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PetSearchCriteria {

    private String name;
    private Float weight;
    private String birthdate;
    private Breed breed;
    private int trainingLevel;
    private Boolean vaccinationStatus;

}
