package com.example.demo.entities;

import com.example.demo.entities.input.OwnerInput;
import com.github.ksuid.Ksuid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "pet_owner", schema = "petscreening_schema")
public class Owner {
    public static final String OWNER_PREFIX = "ow";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "p_id")
    private String pId;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    protected String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    public static Owner buildOwnerFromInput(OwnerInput ownerInput) {
        return Owner.builder()
                .pets(new ArrayList<>())
                .address(ownerInput.getAddress())
                .pId(getNewPId())
                .email(ownerInput.getEmail())
                .phone(ownerInput.getPhone())
                .build();
    }

    private static String getNewPId() {
        return OWNER_PREFIX + Ksuid.newKsuid();
    }
}
