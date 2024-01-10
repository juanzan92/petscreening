package com.example.demo.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetEligibility {
    private String petId;
    private Boolean isValidPet;

}
