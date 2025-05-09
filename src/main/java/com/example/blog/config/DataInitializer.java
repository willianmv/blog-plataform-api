package com.example.blog.config;

import com.example.blog.domain.RoleName;
import com.example.blog.domain.entities.Role;
import com.example.blog.domain.entities.User;
import com.example.blog.repositories.RoleRepository;
import com.example.blog.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ROLE_ADMIN, null)));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ROLE_USER, null)));

        if (!userRepository.existsByEmail("admin@blog.com")) {
            User adminUser = User.builder()
                    .name("Admin")
                    .email("admin@blog.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole, userRole))
                    .build();

            userRepository.save(adminUser);
            System.out.println("ADMIN user created.");
        } else {
            System.out.println("ADMIN user already created.");
        }
    }

}
