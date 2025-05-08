package com.example.blog.services;

import com.example.blog.domain.CreatePostRequestHelper;
import com.example.blog.domain.UpdatePostRequestHelper;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId, LocalDate start, LocalDate end);

    List<Post> getDraftPosts(User user);

    Post getPostById(UUID postId);

    Post cratePost(User user, CreatePostRequestHelper createPostRequestHelper);

    Post updatePost(UUID postId, UpdatePostRequestHelper updatePostRequestHelper);

    void deletePost(UUID postId);

}
