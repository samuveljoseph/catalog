package com.ecommerce.catalog.business.controller;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.service.CategoryService;
import com.ecommerce.catalog.business.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/catalog")
@Tag(name = "Category", description = "Category API")
public class CategoryController {
  @Autowired CategoryService categoryService;

  @Operation(
      summary = "create a new category",
      tags = "Category",
      description = "Api is used for create a new category in the DataBase")
  @PostMapping("/categories")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category) {
    return categoryService
        .createCategory(category)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while creating category : {}, excepion :{}",
                  category,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While creating Category"));
            });
  }

  @Operation(
      summary = "Get a category",
      tags = "Category",
      description = "Api is used for fetch a category from the DataBase By Id")
  @GetMapping("/categories/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Category>> getCategoryById(@PathVariable String categoryId) {
    return categoryService
        .fetchCategoryById(categoryId)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while fetching category by ID: {}, excepion :{}",
                  categoryId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While Fetching Category"));
            });
  }

  @Operation(
      summary = "Delete a category",
      tags = "Category",
      description = "Api is used for Delete a category in the DataBase By Category Id")
  @DeleteMapping("/categories/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Category>> deleteCategoryById(@PathVariable String categoryId) {
    return categoryService
        .deleteCategoryById(categoryId)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while deleting category by ID: {}, excepion :{}",
                  categoryId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While deleting Category"));
            });
  }

  @Operation(
      summary = "Update a category",
      tags = "Category",
      description = "Api is used for update a category in the DataBase By Category Id")
  @PutMapping("/categories/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Category>> updateCategoryById(
      @PathVariable String categoryId, @RequestBody Category categoryRequest) {
    Category category = categoryRequest.toBuilder().id(categoryId).build();
    return categoryService
        .updateCategoryById(category)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while updating category by ID: {}, excepion :{}",
                  categoryId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While updating Category"));
            });
  }
}
