package com.example.blog.controllers;

import com.example.blog.domain.CreatePostRequestHelper;
import com.example.blog.domain.UpdatePostRequestHelper;
import com.example.blog.domain.dtos.CreatePostRequestDto;
import com.example.blog.domain.dtos.PostResponseDto;
import com.example.blog.domain.dtos.UpdatePostRequestDto;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.mappers.PostMapper;
import com.example.blog.security.BlogUserDetails;
import com.example.blog.services.PostService;
import com.example.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final PostMapper postMapper;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> listPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        List<Post> posts = postService.getAllPosts(categoryId, tagId,startDate, endDate);
        List<PostResponseDto> postList = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponseDto>> listDraftPosts(Authentication authentication){

        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        User user = userService.getUserById(userId);

        List<Post> draftPosts = postService.getDraftPosts(user);
        List<PostResponseDto> list = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("postId") UUID postId){
        Post postFound = postService.getPostById(postId);
        return ResponseEntity.ok(postMapper.toDto(postFound));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(Authentication authentication,
            @RequestBody @Valid CreatePostRequestDto createPostDto){

        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        User loggedInUser = userService.getUserById(userId);

        CreatePostRequestHelper createPostRequestHelper = postMapper.toCreatePostRequest(createPostDto);
        Post creatadPost = postService.cratePost(loggedInUser, createPostRequestHelper);
        return new ResponseEntity<>(postMapper.toDto(creatadPost), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable("postId") UUID postId ,
            @RequestBody @Valid UpdatePostRequestDto updatePostRequestDto,
            Authentication authentication){

        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        Post post = postService.getPostById(postId);
        if(!post.getAuthor().getId().equals(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UpdatePostRequestHelper updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(postId, updatePostRequest);
        return ResponseEntity.ok(postMapper.toDto(updatedPost));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") UUID postId, Authentication authentication){

        Post post = postService.getPostById(postId);
        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if(!post.getAuthor().getId().equals(userId) && !isAdmin){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}
