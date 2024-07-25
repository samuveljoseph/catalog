package com.ecommerce.catalog.business.repository;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.adapter.CategoryAdapter;
import com.ecommerce.catalog.business.mapper.CategoryMongoToCategoryMapper;
import com.ecommerce.catalog.business.mapper.CategoryToCategoryMongoMapper;
import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.util.CommonUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CategoryRepo {

  @Autowired CategoryAdapter categoryAdapter;

  public Mono<Category> createCategory(Category category) {
    CategoryMongo categoryMongo =
        Mappers.getMapper(CategoryToCategoryMongoMapper.class).map(category);
    String currentDateTime=CommonUtil.getCurrentDateTime();
    categoryMongo.setCreatedDate(currentDateTime);
    categoryMongo.setUpdatedDate(currentDateTime);
    return categoryAdapter
        .createCategory(categoryMongo)
        .map(
            categoryMongo1 ->
                Mappers.getMapper(CategoryMongoToCategoryMapper.class).map(categoryMongo1))
        .doOnError(
            throwable ->
                Mono.error(new BusinessException("Error occured While creating category")));
  }

  public Mono<CategoryMongo> fetchCategoryById(String categoryId) {
    return categoryAdapter
        .fetchCategoryById(categoryId)
        .switchIfEmpty(Mono.empty())
        .doOnError(throwable -> Mono.error(throwable));
  }

  public Mono<CategoryMongo> deleteCategoryById(String categoryId) {
    return categoryAdapter
        .deleteCategoryById(categoryId)
        .switchIfEmpty(Mono.empty())
        .doOnError(
            throwable -> Mono.error(new BusinessException("Error occure While deleting category")));
  }

  public Mono<Category> updateCategoryById(CategoryMongo categoryMongo) {
    return categoryAdapter
        .updateCategoryById(categoryMongo)
        .map(
            categoryMongo1 ->
                Mappers.getMapper(CategoryMongoToCategoryMapper.class).map(categoryMongo1))
        .doOnError(throwable -> Mono.error(throwable));
  }
}
