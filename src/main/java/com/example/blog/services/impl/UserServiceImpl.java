package com.example.blog.services.impl;

import com.example.blog.domain.RoleName;
import com.example.blog.domain.entities.Role;
import com.example.blog.domain.entities.User;
import com.example.blog.repositories.RoleRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: "+userId));
    }

    @Override
    public User createUser(User userToCreate) {
        if(userRepository.existsByEmail(userToCreate.getEmail())){
            throw new IllegalArgumentException("This e-mail is already registered: "+userToCreate.getEmail());
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Role User not found") );

        User newUser = User.builder()
                .name(userToCreate.getName())
                .email(userToCreate.getEmail())
                .password(passwordEncoder.encode(userToCreate.getPassword()))
                .roles(Set.of(role))
                .build();
        return userRepository.save(newUser);
    }
}
