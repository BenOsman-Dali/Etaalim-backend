package com.example.Etaalim_backend.user.dto;

import com.example.Etaalim_backend.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String firstName;

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String lastName;

    @Email(message = "L'adresse email n'est pas valide")
    @Size(max = 255, message = "L'adresse email ne doit pas dépasser 255 caractères")
    private String email;

    private Role role;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(
            String firstName,
            String lastName,
            String email,
            Role role
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}