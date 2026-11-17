package com.example.Etaalim_backend.user.service;

import com.example.Etaalim_backend.user.dto.CreateUserRequest;
import com.example.Etaalim_backend.user.dto.UpdateUserRequest;
import com.example.Etaalim_backend.user.dto.UserResponse;
import com.example.Etaalim_backend.user.entity.User;
import com.example.Etaalim_backend.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(CreateUserRequest request) {
        String normalizedEmail = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalStateException(
                    "Un utilisateur avec cet email existe déjà"
            );
        }

        User user = new User();
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(UUID id) {
        return toResponse(getUserEntity(id));
    }

    public UserResponse update(
            UUID id,
            UpdateUserRequest request
    ) {
        User user = getUserEntity(id);

        if (request.getFirstName() != null
                && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName().trim());
        }

        if (request.getLastName() != null
                && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName().trim());
        }

        if (request.getEmail() != null
                && !request.getEmail().isBlank()) {

            String normalizedEmail = normalizeEmail(request.getEmail());

            boolean emailUsedByAnotherUser =
                    userRepository.existsByEmailIgnoreCaseAndIdNot(
                            normalizedEmail,
                            id
                    );

            if (emailUsedByAnotherUser) {
                throw new IllegalStateException(
                        "Un autre utilisateur utilise déjà cet email"
                );
            }

            user.setEmail(normalizedEmail);
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        /*
         * Le mot de passe n'est pas modifié ici.
         * Il devra être géré par un endpoint dédié.
         */

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    public UserResponse setEnabled(
            UUID id,
            boolean enabled
    ) {
        User user = getUserEntity(id);
        user.setEnabled(enabled);

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    public void delete(UUID id) {
        User user = getUserEntity(id);
        userRepository.delete(user);
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Utilisateur introuvable avec l'identifiant : " + id
                ));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}