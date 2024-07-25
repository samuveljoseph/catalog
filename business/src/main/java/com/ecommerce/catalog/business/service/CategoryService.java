package com.ecommerce.catalog.business.service;

import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.util.ApiResponse;
import reactor.core.publisher.Mono;

public interface CategoryService {
  Mono<ApiResponse<Category>> createCategory(Category category);

  Mono<ApiResponse<Category>> fetchCategoryById(String categoryId);

  Mono<ApiResponse<Category>> deleteCategoryById(String categoryId);

  Mono<ApiResponse<Category>> updateCategoryById(Category category);
}
