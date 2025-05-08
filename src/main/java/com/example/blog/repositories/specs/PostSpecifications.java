package com.example.blog.repositories.specs;

import com.example.blog.domain.PostStatus;
import com.example.blog.domain.entities.Category;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.Tag;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PostSpecifications {

    public static Specification<Post> hasStatus(PostStatus status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Post> hasCategory(Category category){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Post> hasTag(Tag tag){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isMember(tag, root.get("tags"));
    }

    public static Specification<Post> createdAtBetween(LocalDate start, LocalDate end){
        return (root, query, criteriaBuilder) ->{
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
            return criteriaBuilder.between(root.get("createdAt"), startDateTime, endDateTime);
        };
    }

}
