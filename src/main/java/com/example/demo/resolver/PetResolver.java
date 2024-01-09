package com.example.demo.resolver;

import com.example.demo.entities.Pet;
import com.example.demo.repository.PetRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.entities.enums.Breed.POODLE;

@Component
public class PetResolver implements GraphQLQueryResolver {

    private final PetRepository petRepository;

    public PetResolver(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findPetsUnderWeight(Float weight) {
        return petRepository.findByWeightLessThan(weight);
    }

    public List<Pet> findVaccinatedPets(Boolean status) {
        return petRepository.findByVaccinationStatus(status);
    }

    public List<Pet> findNonPoodlePets() {
        return petRepository.findByBreedNot(POODLE);
    }

    public Pet petById(String id) {
        return petRepository.findByPId(id).orElse(null);
    }


    public List<Pet> findPetsWithTrainingLevel(Integer minTrainingLevel) {
        return petRepository.findByTrainingLevelGreaterThanEqual(minTrainingLevel);
    }
}
