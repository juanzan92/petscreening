package com.example.demo.controller;

import com.example.demo.entities.Owner;
import com.example.demo.entities.Pet;
import com.example.demo.entities.PetEligibility;
import com.example.demo.entities.PetSearchCriteria;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.resolver.PetResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/graphql")
@RequiredArgsConstructor
public class PetController {
    private final PetRepository petRepository;
    private final PetResolver petResolver;
    private final OwnerRepository ownerRepository;

    @QueryMapping
    public Pet petById(@Argument String p_id) {
        return petRepository.findByPId(p_id).orElse(null);
    }

    @QueryMapping
    public List<Pet> searchPets(@Argument PetSearchCriteria criteria) {
        return petResolver.search(criteria);
    }

    @QueryMapping
    public PetEligibility checkPetEligibility(@Argument String petId) {
        return petResolver.checkPetViability(petId);
    }

    @MutationMapping
    private Pet addPet(@Argument String name,
                       @Argument Float weight,
                       @Argument Boolean vaccinated,
                       @Argument String breed,
                       @Argument int trainingLevel,
                       @Argument String ownerId) {

        return petResolver.createPet(name, weight, vaccinated, breed, trainingLevel, ownerId);

    }

    @SchemaMapping
    public Owner owner(Pet pet) {
        return ownerRepository.findByPId(pet.getOwner().getPId()).orElse(null);
    }
}
