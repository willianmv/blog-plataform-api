package com.example.blog.controllers;

import com.example.blog.domain.dtos.CreateTagsRequestDto;
import com.example.blog.domain.dtos.TagResponseDto;
import com.example.blog.mappers.TagMapper;
import com.example.blog.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> listTags(){
        List<TagResponseDto> tags = tagService.getTags().stream()
                .map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTags(@RequestBody @Valid CreateTagsRequestDto dto){
        List<TagResponseDto> tags = tagService.crateTags(dto.names()).stream()
                .map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
