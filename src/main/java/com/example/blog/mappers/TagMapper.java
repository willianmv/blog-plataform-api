package com.example.blog.mappers;

import com.example.blog.domain.PostStatus;
import com.example.blog.domain.dtos.TagResponseDto;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponseDto toDto(Tag tag);

    @Named("calculatePostCount")
    default int calculatePostCount(Set<Post> posts){
        if(posts == null) return 0;
        return (int) posts.stream().filter(post -> post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }

}
