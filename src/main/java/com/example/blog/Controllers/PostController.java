package com.example.blog.Controllers;

import com.example.blog.domain.dtos.PostResponseDto;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.mappers.PostMapper;
import com.example.blog.services.PostService;
import com.example.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false)UUID tagId){
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostResponseDto> postList = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponseDto>> listDraftPosts(@RequestAttribute UUID userId){
        User user  = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(user);
        List<PostResponseDto> list = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

}
