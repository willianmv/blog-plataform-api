package com.example.blog.mappers;

import com.example.blog.domain.dtos.CreateRoleRequestDto;
import com.example.blog.domain.dtos.RoleResponseDto;
import com.example.blog.domain.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleResponseDto toDto(Role role);

    Role toRole(CreateRoleRequestDto dto);
}
