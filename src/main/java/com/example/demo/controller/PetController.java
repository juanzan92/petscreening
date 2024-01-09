package com.example.demo.controller;

import com.example.demo.entities.Owner;
import com.example.demo.entities.Pet;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/graphql")
@RequiredArgsConstructor
public class PetController {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @QueryMapping
    public Pet petById(@Argument String p_id) {
        return petRepository.findByPId(p_id).orElse(null);
    }

    @SchemaMapping
    public Owner owner(Pet pet) {
        return ownerRepository.findByPId(pet.getOwner().getPId()).orElse(null);
    }
}
