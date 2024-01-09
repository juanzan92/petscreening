package com.example.demo.entities;

import com.example.demo.entities.enums.Breed;
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

import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "pet", schema = "petscreening_schema")
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "p_id")
    private String pId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private String weight;

    @Column(name = "training_level")
    private String trainingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "breed")
    private Breed breed;

    @Column(name = "vaccination_status")
    private String vaccinationStatus;

    @ManyToOne(targetEntity = Owner.class)
    @JoinColumn(name = "pet_owner", nullable = false, referencedColumnName = "p_id")
    private Owner owner;
}
