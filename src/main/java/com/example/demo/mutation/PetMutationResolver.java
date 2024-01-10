package com.example.demo.mutation;

import com.example.demo.entities.Owner;
import com.example.demo.entities.Pet;
import com.example.demo.entities.enums.Breed;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PetMutationResolver implements GraphQLMutationResolver {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetMutationResolver(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public Pet addPet(String name, Float weight, Boolean vaccinated, String breed, Integer trainingLevel, String ownerId) {
        log.info("PetMutationResolver#addPet - name={} weight={} vaccinated={} breed={} trainingLevel={} ownerId={}"
                , name, weight, vaccinated, breed, trainingLevel, ownerId);

        Owner owner = ownerRepository.findByPId(ownerId)
                .orElseThrow(() -> NotFoundException.getException("owner", ownerId));

        Pet newPet = Pet.builder()
                .name(name)
                .weight(weight)
                .trainingLevel(trainingLevel)
                .vaccinationStatus(vaccinated)
                .breed(Breed.valueOf(breed))
                .owner(owner)
                .build();

        return petRepository.save(newPet);
    }
}
