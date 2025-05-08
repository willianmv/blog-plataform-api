package com.example.blog.mappers;

import com.example.blog.domain.CreatePostRequestHelper;
import com.example.blog.domain.UpdatePostRequestHelper;
import com.example.blog.domain.dtos.CreatePostRequestDto;
import com.example.blog.domain.dtos.PostResponseDto;
import com.example.blog.domain.dtos.UpdatePostRequestDto;
import com.example.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostResponseDto toDto(Post post);

    CreatePostRequestHelper toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequestHelper toUpdatePostRequest(UpdatePostRequestDto dto);

}
