package com.example.demo.entities;

import com.example.demo.entities.enums.Breed;
import com.example.demo.entities.input.PetInput;
import com.github.ksuid.Ksuid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "pet", schema = "petscreening_schema")
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    public static final String PET_PREFIX = "pet-";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "p_id")
    private String pId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "training_level")
    private int trainingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "breed")
    private Breed breed;

    @Column(name = "vaccination_status")
    private Boolean vaccinationStatus;

    @ManyToOne(targetEntity = Owner.class)
    @JoinColumn(name = "pet_owner", nullable = false, referencedColumnName = "p_id")
    private Owner owner;


    public static Pet buildPetFromInput(PetInput petInput, Owner owner) {
        return Pet.builder()
                .vaccinationStatus(petInput.getVaccinated())
                .birthdate(Date.from(Instant.now()))
                .pId(getNewPId())
                .name(petInput.getName())
                .weight(petInput.getWeight())
                .breed(Breed.valueOf(petInput.getBreed()))
                .trainingLevel(petInput.getTrainingLevel())
                .owner(owner)
                .build();
    }

    private static String getNewPId() {
        return PET_PREFIX + Ksuid.newKsuid();
    }
}
