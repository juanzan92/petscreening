package com.example.demo.repository;

import com.example.demo.entities.Owner;
import com.example.demo.entities.Pet;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    Optional<Pet> findByOwner(Owner owner);

    @Query("select p from Pet p where p.pId = ?1")
    Optional<Pet> findByPId(String pId);

    List<Pet> findAll(@NotNull Specification<Pet> specification, Sort sort);
}
