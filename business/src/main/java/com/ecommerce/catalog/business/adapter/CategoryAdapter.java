package com.ecommerce.catalog.business.adapter;

import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import reactor.core.publisher.Mono;

public interface CategoryAdapter {
  Mono<CategoryMongo> createCategory(CategoryMongo categoryMongo);

  Mono<CategoryMongo> fetchCategoryById(String categoryId);

  Mono<CategoryMongo> deleteCategoryById(String categoryId);

  Mono<CategoryMongo> updateCategoryById(CategoryMongo categoryMongo);
}
