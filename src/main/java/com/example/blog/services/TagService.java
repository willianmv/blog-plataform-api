package com.example.blog.services;

import com.example.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<Tag> getTags();

    List<Tag> crateTags(Set<String> tagNames);

    void deleteTag(UUID id);

    Tag getTagById(UUID id);

}
