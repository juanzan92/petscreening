package com.example.demo.entities.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OwnerInput {
    private String pId;
    private String name;
    private String email;
    private String address;
    private String phone;
}
