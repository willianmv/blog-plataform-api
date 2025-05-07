package com.example.blog.Controllers;

import com.example.blog.domain.dtos.CategoryDto;
import com.example.blog.domain.dtos.CreateCategoryRequestDto;
import com.example.blog.domain.entities.Category;
import com.example.blog.mappers.CategoryMapper;
import com.example.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<CategoryDto> categories = categoryService.listCategories()
                .stream().map(categoryMapper::toDto).toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryRequestDto dto){
        Category category = categoryMapper.toEntity(dto);
        Category categorySaved = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryMapper.toDto(categorySaved),  HttpStatus.CREATED);
    }

}
