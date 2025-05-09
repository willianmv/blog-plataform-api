package com.example.blog.services;

import com.example.blog.domain.entities.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<Role> getRoles();

    Role createRole(Role role);

    void deleteRole(UUID roleId);

}
