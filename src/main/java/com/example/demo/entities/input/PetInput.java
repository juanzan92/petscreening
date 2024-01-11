package com.example.demo.entities.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Data
public class PetInput {

    private String name;
    private Float weight;
    private Boolean vaccinated;
    private String breed;

    @Max(10)
    @Min(0)
    private int trainingLevel;

    private OwnerInput ownerInput;
}
