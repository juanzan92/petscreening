package com.example.demo.repository;

import com.example.demo.entities.Owner;
import com.example.demo.entities.Pet;
import com.example.demo.entities.enums.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByOwner(Owner owner);

    @Query("select p from Pet p where p.pId = ?1")
    Optional<Pet> findByPId(String pId);

    @Query("select p from Pet p where p.weight < ?1")
    List<Pet> findByWeightLessThan(Float weight);

    List<Pet> findByVaccinationStatus(Boolean vaccinationStatus);

    List<Pet> findByBreedNot(Breed breed);

    List<Pet> findByTrainingLevelGreaterThanEqual(Integer minTrainingLevel);

}
