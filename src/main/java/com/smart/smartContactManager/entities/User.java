package com.smart.smartContactManager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name field is required.")
    @Size(max = 50, message="Name cannot exceed 50 charactes.")
    private String name;

    @Size(min = 4, message="Password too short—minimum 4 characters required.")
    private String password;

    @Column(unique = true)
    @Email(message = "Invalid email format—check for missing '@' or domain.")
    private String email;

    private String role;

    private boolean enabled;

    private String about;

    @Column(length = 500)
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();
}
