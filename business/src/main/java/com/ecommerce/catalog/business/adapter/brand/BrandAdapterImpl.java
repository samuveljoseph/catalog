package com.ecommerce.catalog.business.adapter.brand;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.model.mongo.brand.BrandMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class BrandAdapterImpl implements BrandAdapter {

  @Autowired private ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Mono<BrandMongo> createBrand(BrandMongo brandMongo) {
    Query query = Query.query(Criteria.where("name").is(brandMongo.getName()));
    return reactiveMongoTemplate
        .findOne(query, BrandMongo.class, "brands")
        .filter(Objects::nonNull)
        .flatMap(
            brandMongo1 -> {
              log.error(
                  "Brand Name : {} already assigned to brandId: {}",
                  brandMongo1.getName(),
                  brandMongo1.getId());
              return Mono.<BrandMongo>error(new BusinessException("Brand name already exists"));
            })
        .switchIfEmpty(
            Mono.defer(
                () ->
                    reactiveMongoTemplate
                        .insert(brandMongo, "brands")
                        .doOnError(
                            throwable -> {
                              log.error(
                                  "Error occured when creating brand : {}, exception {}",
                                  brandMongo,
                                  throwable);
                              throw new BusinessException("Error occured While creating brand");
                            })));

    /* .onErrorResume(throwable -> { log.error("Error occured when creating brand : {}, exception {}",brandMongo,throwable);
    return Mono.error(new BusinessException("Error occure While creating brand"));});*/
  }

  @Override
  public Mono<BrandMongo> fetchBrandById(String brandId) {
    return reactiveMongoTemplate
        .findById(brandId, BrandMongo.class, "brands")
        .doOnError(
            throwable -> {
              log.error(
                  "Error occured while getting brand : {} exception : {}", brandId, throwable);
              throw new BusinessException("Error occure While fetching brand");
            });
  }

  @Override
  public Mono<BrandMongo> deleteBrandById(String brandId) {
    Query deleteQuerry = new Query(Criteria.where("id").is(brandId));
    return reactiveMongoTemplate
        .findAndRemove(deleteQuerry, BrandMongo.class, "brands")
        .doOnError(
            throwable -> {
              log.error(
                  "Error occured while deleting brand : {} exception : {}", brandId, throwable);
              throw new BusinessException("Error occure While Deleting brand");
            });
  }

  @Override
  public Mono<BrandMongo> updateBrandById(BrandMongo brandMongo) {
    Query query =
        Query.query(
            Criteria.where("name").is(brandMongo.getName()).and("id").ne(brandMongo.getId()));
    return reactiveMongoTemplate
        .findOne(query, BrandMongo.class, "brands")
        .filter(Objects::nonNull)
        .flatMap(
            brandMongo1 -> {
              log.error(
                  "Brand Name : {} already assigned to brandId: {}",
                  brandMongo1.getName(),
                  brandMongo1.getId());
              return Mono.<BrandMongo>error(new BusinessException("Brand name already exists"));
            })
        .switchIfEmpty(
            Mono.defer(
                () ->
                    reactiveMongoTemplate
                        .save(brandMongo, "brands")
                        .doOnError(
                            throwable -> {
                              log.error(
                                  "Error occured when updating brand : {}, exception {}",
                                  brandMongo,
                                  throwable);
                              throw new BusinessException("Error occured While updating brand");
                            })));
  }
}
