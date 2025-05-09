package com.example.blog.Controllers;

import com.example.blog.domain.dtos.CreateRoleRequestDto;
import com.example.blog.domain.dtos.RoleResponseDto;
import com.example.blog.domain.entities.Role;
import com.example.blog.mappers.RoleMapper;
import com.example.blog.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getRoles(){
        List<RoleResponseDto> roles = roleService.getRoles().stream().map(roleMapper::toDto).toList();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid CreateRoleRequestDto dto){
        Role role = roleMapper.toRole(dto);
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(roleMapper.toDto(createdRole));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId")UUID roleId){
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}
