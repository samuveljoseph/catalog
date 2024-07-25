package com.ecommerce.catalog.business.service;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.mapper.CategoryMongoToCategoryMapper;
import com.ecommerce.catalog.business.mapper.CategoryToCategoryMongoMapper;
import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.repository.CategoryRepo;
import com.ecommerce.catalog.business.util.ApiResponse;
import com.ecommerce.catalog.business.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
  @Autowired CategoryRepo categoryRepo;

  @Override
  public Mono<ApiResponse<Category>> createCategory(Category category) {
    return categoryRepo
        .createCategory(category)
        .map(category1 -> new ApiResponse<>("success", category1, "Category created successfully"))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {
                log.info(
                    "Successfully deleted category with ID: {}",
                    categoryApiResponse.getData().getId());
              }
            })
        .onErrorResume(
            throwable -> {
              log.error(
                  "Error occured when creating category : {}, exception {}", category, throwable);
              return Mono.error(new BusinessException("Error occure While creating category"));
            });
  }

  @Override
  public Mono<ApiResponse<Category>> fetchCategoryById(String categoryId) {
    return categoryRepo
        .fetchCategoryById(categoryId)
        .map(
            categoryMongo ->
                Mappers.getMapper(CategoryMongoToCategoryMapper.class).map(categoryMongo))
        .map(category1 -> new ApiResponse<>("success", category1, "Category fetched successfully"))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {

                log.info(
                    "Successfully fetched a category with ID: {}",
                    categoryApiResponse.getData().getId());
              }
            })
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  log.error("category with category ID:" + categoryId + "not found");
                  return Mono.just(new ApiResponse<Category>("error", null, "category not found"));
                }))
        .onErrorResume(
            throwable -> {
              log.error(
                  "Error occured while fetching category by ID: {}, excepion :{}",
                  categoryId,
                  throwable);
              return Mono.error(new BusinessException("Error occured While Fetching Category"));
            });
  }

  @Override
  public Mono<ApiResponse<Category>> deleteCategoryById(String categoryId) {
    return categoryRepo
        .deleteCategoryById(categoryId)
        .map(
            categoryMongo ->
                new ApiResponse<Category>("susccess", null, "category deleted successfully"))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  log.error("category with category ID:" + categoryId + "not found");
                  return Mono.just(new ApiResponse<Category>("error", null, "category not found"));
                }))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {

                log.info("Successfully deleted category with ID: {}", categoryId);
              }
            })
        .onErrorResume(
            throwable -> {
              log.error(
                  "Error occured when deleting category  exception {}, categoryId: {}",
                  throwable,
                  categoryId);
              return Mono.error(new BusinessException("Error occured While deleting category"));
            });
  }

  @Override
  public Mono<ApiResponse<Category>> updateCategoryById(Category category) {
    return categoryRepo
        .fetchCategoryById(category.getId())
        .flatMap(
            category1 ->
                getUpdatedCategoryMongo(category1, category)
                    .flatMap(categoryMongo -> categoryRepo.updateCategoryById(categoryMongo)))
        .map(category1 -> new ApiResponse<>("susccess", category1, "Category updated successfully"))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                    log.error("category with category ID:not found");
                  return Mono.just(new ApiResponse<Category>("error", null, "category not found"));
                }))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse.getData())) {
                log.info(
                    "Successfully updated category with ID: {}",
                    category.getId());
              }
            })
        .onErrorResume(
            throwable -> {
              log.error(
                  "Error occured when updating category : {}, exception {}", category, throwable);
              return Mono.error(new BusinessException("Error occure While updating category"));
            });
  }

  private Mono<CategoryMongo> getUpdatedCategoryMongo(
      CategoryMongo categoryMongo, Category category) {
    CategoryMongo updatedCategory =
        Mappers.getMapper(CategoryToCategoryMongoMapper.class).updateMapper(category, categoryMongo);
    updatedCategory.setId(categoryMongo.getId()) ;
    updatedCategory.setUpdatedDate(CommonUtil.getCurrentDateTime());
    updatedCategory.setCreatedDate(categoryMongo.getCreatedDate());
    return Mono.just(updatedCategory);
  }
}
