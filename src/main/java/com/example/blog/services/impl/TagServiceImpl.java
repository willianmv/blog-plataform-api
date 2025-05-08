package com.example.blog.services.impl;

import com.example.blog.domain.entities.Tag;
import com.example.blog.repositories.TagRepository;
import com.example.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> crateTags(Set<String> tagNames) {

        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder().name(name).posts(new HashSet<>()).build())
                .toList();

        if(!newTags.isEmpty()) tagRepository.saveAll(newTags);

        return new ArrayList<>(existingTags);
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getPosts().isEmpty()) throw new IllegalStateException("Cannot delete tag with posts");
            tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not fount with ID: "+ id));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> tagIds) {
        List<Tag> foundTags = tagRepository.findAllById(tagIds);
        if(foundTags.size() != tagIds.size()){
            throw new EntityNotFoundException("Not all specified tag ID's exist");
        }
        return foundTags;
    }
}
