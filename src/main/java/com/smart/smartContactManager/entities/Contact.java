package com.smart.smartContactManager.entities;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("cId")
    private int cId;

    @NotBlank(message = "Name is required")
    private String name;

    private String secondName;

    @Email(message = "Invalid email format—check for missing '@' or domain.")
    @NotBlank(message = "Email is required")
    private String email;

    private String image;

    @Column(length = 500)
    private String description;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String work;

    @ManyToOne
    @JsonIgnore
    private User user;
}
