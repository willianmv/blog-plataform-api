package com.example.blog.mappers;

import com.example.blog.domain.dtos.RegisterRequestDto;
import com.example.blog.domain.dtos.UserResponseDto;
import com.example.blog.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(RegisterRequestDto dto);

    UserResponseDto toDto(User user);
}
