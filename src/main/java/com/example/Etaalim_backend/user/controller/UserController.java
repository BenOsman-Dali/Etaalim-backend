package com.example.Etaalim_backend.user.controller;

import com.example.Etaalim_backend.user.dto.CreateUserRequest;
import com.example.Etaalim_backend.user.dto.UpdateUserRequest;
import com.example.Etaalim_backend.user.dto.UserResponse;
import com.example.Etaalim_backend.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * POST /api/v1/users
     * Crée un nouvel utilisateur.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return userService.create(request);
    }

    /*
     * GET /api/v1/users
     * Retourne tous les utilisateurs.
     */
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    /*
     * GET /api/v1/users/{id}
     * Retourne un utilisateur par son identifiant.
     */
    @GetMapping("/{id}")
    public UserResponse findById(
            @PathVariable UUID id
    ) {
        return userService.findById(id);
    }

    /*
     * PUT /api/v1/users/{id}
     * Modifie les informations d'un utilisateur.
     */
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return userService.update(id, request);
    }

    /*
     * PATCH /api/v1/users/{id}/status?enabled=true
     * Active ou suspend un compte.
     */
    @PatchMapping("/{id}/status")
    public UserResponse setEnabled(
            @PathVariable UUID id,
            @RequestParam boolean enabled
    ) {
        return userService.setEnabled(id, enabled);
    }

    /*
     * DELETE /api/v1/users/{id}
     * Supprime un utilisateur.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}