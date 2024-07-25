package com.ecommerce.catalog.business.service;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.model.request.brand.Brand;
import com.ecommerce.catalog.business.repository.BrandRepo;
import com.ecommerce.catalog.business.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

  @Autowired BrandRepo brandRepo;

  @Override
  public Mono<ApiResponse<Brand>> createBrand(Brand brand) {
    return brandRepo
        .createBrand(brand)
        .map(brand1 -> new ApiResponse<>("success", brand1, "Brand created successfully"))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {
                log.info(
                    "Successfully created brand : {} with brandId: {}",
                    categoryApiResponse.getData(),
                    categoryApiResponse.getData().getId());
              }
            })
        .onErrorResume(
            throwable -> {
              log.error("Error occured when creating brand : {}, exception {}", brand, throwable);
              return Mono.error(new BusinessException("Error occured While creating brand"));
            });
  }

  @Override
  public Mono<ApiResponse<Brand>> updateBrand(Brand brand) {
    return brandRepo
        .updateBrandById(brand)
        .map(brand1 -> new ApiResponse<>("success", brand1, "Brand updated successfully"))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  log.error("brand with brand ID:" + brand.getId() + "not found");
                  return Mono.just(new ApiResponse<Brand>("error", null, "brand not found"));
                }))
        .doOnSuccess(
            brandApiResponse -> {
              if (Objects.nonNull(brandApiResponse.getData())) {
                log.info(
                    "Successfully updated brand with ID: {}", brandApiResponse.getData().getId());
              }
            })
        .onErrorResume(
            throwable -> {
              log.error(
                  "update brand failed with brand id: {}, exception : {}",
                  brand.getId(),
                  throwable);
              return Mono.error(new BusinessException("Error occured While updating brand"));
            });
  }

  @Override
  public Mono<ApiResponse<Brand>> fetchBrandById(String brandId) {
    return brandRepo
        .fetchBrandById(brandId)
        .map(brand -> new ApiResponse<>("success", brand, "Brand fetched successfully"))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  log.error("brand with brand ID:" + brandId + "not found");
                  return Mono.just(new ApiResponse<Brand>("error", null, "brand not found"));
                }))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {
                log.info("Successfully fetched brand with ID: {}", brandId);
              }
            })
        .onErrorResume(
            throwable -> {
              log.error("get brand failed with brand id: {}, exception : {}", brandId, throwable);
              return Mono.error(new BusinessException("Error occured While Fetching brand"));
            });
  }

  @Override
  public Mono<ApiResponse<Brand>> deleteBrandById(String brandId) {
    return brandRepo
        .deleteBrandById(brandId)
        .map(brandMongo -> new ApiResponse<Brand>("success", null, "brand deleted successfully"))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  log.error("brand with brand ID:" + brandId + "not found");
                  return Mono.just(new ApiResponse<Brand>("error", null, "brand not found"));
                }))
        .doOnSuccess(
            categoryApiResponse -> {
              if (Objects.nonNull(categoryApiResponse)) {
                log.info("Successfully deleted brand with ID: {}", brandId);
              }
            })
        .onErrorResume(
            throwable -> {
              log.error(
                  "delete brand failed with brand id: {}, exception : {}", brandId, throwable);
              return Mono.error(new BusinessException("Error occured While Deleting brand"));
            });
  }
}
