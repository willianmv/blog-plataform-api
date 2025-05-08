package com.example.blog.services.impl;

import com.example.blog.domain.CreatePostRequestHelper;
import com.example.blog.domain.PostStatus;
import com.example.blog.domain.UpdatePostRequestHelper;
import com.example.blog.domain.entities.Category;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.Tag;
import com.example.blog.domain.entities.User;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.specs.PostSpecifications;
import com.example.blog.services.CategoryService;
import com.example.blog.services.PostService;
import com.example.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId, LocalDate start, LocalDate end) {
        Specification<Post> spec = PostSpecifications.hasStatus(PostStatus.PUBLISHED);

        if(categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            spec = spec.and(PostSpecifications.hasCategory(category));
        }

        if(tagId != null){
            Tag tag = tagService.getTagById(tagId);
            spec = spec.and(PostSpecifications.hasTag(tag));
        }

        if(start != null && end != null){
            spec = spec.and(PostSpecifications.createdAtBetween(start, end));
        }

        return postRepository.findAll(spec);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAll(PostSpecifications.hasStatus(PostStatus.DRAFT));
    }

    @Override
    public Post getPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: "+postId));
    }

    @Transactional
    @Override
    public Post cratePost(User user, CreatePostRequestHelper createPostRequestHelper) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequestHelper.getTitle());
        newPost.setContent(createPostRequestHelper.getContent());
        newPost.setStatus(createPostRequestHelper.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequestHelper.getContent()));

        Category category = categoryService.getCategoryById(createPostRequestHelper.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequestHelper.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    private int calculateReadingTime(String content){
        if(content == null || content.isBlank()) return 0;
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount/ WORDS_PER_MINUTE);
    }

    @Transactional
    @Override
    public Post updatePost(UUID postId, UpdatePostRequestHelper updatePostRequestHelper) {
        Post existingPost = postRepository.findById(postId).
                orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        existingPost.setTitle(updatePostRequestHelper.getTitle());
        existingPost.setContent(updatePostRequestHelper.getContent());
        existingPost.setStatus(updatePostRequestHelper.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequestHelper.getContent()));

        UUID categoryId = updatePostRequestHelper.getCategoryId();
        if(!existingPost.getCategory().getId().equals(categoryId)){
            Category newCategory = categoryService.getCategoryById(categoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingPostTags = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTags = updatePostRequestHelper.getTagIds();
        if(!existingPostTags.equals(updatePostRequestTags)){
            List<Tag> newTags = tagService.getTagByIds(updatePostRequestTags);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(UUID postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
    }
}
