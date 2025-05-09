package com.example.blog.services;

import com.example.blog.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User getUserById(UUID userId);

    User createUser(User user);

}
