package com.ecommerce.catalog.business.adapter;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CategoryAdapterImpl implements CategoryAdapter {
  @Autowired private ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Mono<CategoryMongo> createCategory(CategoryMongo categoryMongo) {
    return reactiveMongoTemplate
        .insert(categoryMongo, "Category")
        .doOnError(
            throwable -> {
              log.error(
                  "Error occured when creating category : {}, exception {}",
                  categoryMongo,
                  throwable);
              throw new BusinessException("Error occured While creating category");
            });
    /*.onErrorResume(throwable -> { log.error("Error occured when creating category : {}, exception {}",categoryMongo,throwable);
    return Mono.error(new BusinessException("Error occure While creating category"));});*/
  }

  @Override
  public Mono<CategoryMongo> fetchCategoryById(String categoryId) {
    return reactiveMongoTemplate
        .findById(categoryId, CategoryMongo.class, "Category")
        .doOnError(
            throwable -> {
              log.error("Error occured while fetching category by ID: {}", categoryId);
              throw new BusinessException("Error occured While Fetching Category");
            });
  }

  @Override
  public Mono<CategoryMongo> deleteCategoryById(String categoryId) {
    Query deleteQuerry = new Query(Criteria.where("_id").is(categoryId));
    return reactiveMongoTemplate
        .findAndRemove(deleteQuerry, CategoryMongo.class, "Category")
        .doOnError(
            throwable -> {
              log.error(
                  "Error occured when deleting category  exception {}, categoryId: {}",
                  throwable,
                  categoryId);
              throw new BusinessException("Error occured While deleting category");
            });
    /*        .onErrorResume(
    throwable -> {
      log.error(
          "Error occured when deleting category  exception {}, categoryId: {}",
          throwable,
          categoryId);
      return Mono.error(new BusinessException("Error occure While deleting category"));
    });*/
  }

  @Override
  public Mono<CategoryMongo> updateCategoryById(CategoryMongo categoryMongo) {
    return reactiveMongoTemplate
        .save(categoryMongo, "Category")
        .doOnError(
            throwable -> {
              log.error(
                  "Error occured when updating category : {}, exception {}",
                  categoryMongo,
                  throwable);
              throw new BusinessException("Error occured While updating category");
            });
    /*                .onErrorResume(throwable -> { log.error("Error occured when updating category : {}, exception {}",categoryMongo,throwable);
    return Mono.error(new BusinessException("Error occure While updating category"));});*/
  }
}
