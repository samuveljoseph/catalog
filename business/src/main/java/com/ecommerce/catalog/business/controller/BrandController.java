package com.ecommerce.catalog.business.controller;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.model.request.brand.Brand;
import com.ecommerce.catalog.business.service.BrandService;
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
@Tag(name = "Brand", description = "Brand API")
public class BrandController {
  @Autowired BrandService brandService;

  @Operation(
      summary = "create a new Brand",
      tags = "Brand",
      description = "Api is used for create a new Brand in the DataBase")
  @PostMapping("/brands")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ApiResponse<Brand>> createBrand(@Valid @RequestBody Brand brand) {
    return brandService
        .createBrand(brand)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while creating brand: {}, excepion :{}",
                  brand,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While creating brand"));
            });
  }

  @Operation(
      summary = "Get a Brand",
      tags = "Brand",
      description = "Api is used for get a Brand from the DataBase")
  @GetMapping("/brands/{brandId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Brand>> getBrandById(@PathVariable String brandId) {
    return brandService
        .fetchBrandById(brandId)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while fetching brand by id: {}, excepion :{}",
                  brandId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While fetching brand"));
            });
  }

  @Operation(
      summary = "Delete a Brand",
      tags = "Brand",
      description = "Api is used for Delete a Brand from the DataBase")
  @DeleteMapping("/brands/{brandId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Brand>> deleteBrandById(@PathVariable String brandId) {
    return brandService
        .deleteBrandById(brandId)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while deleting brand by id: {}, excepion :{}",
                  brandId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While deleting brand"));
            });
  }

  @Operation(
      summary = "Update a Brand",
      tags = "Brand",
      description = "Api is used for update a Brand in the DataBase")
  @PutMapping("/brands/{brandId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ApiResponse<Brand>> updateBrandById(
      @PathVariable String brandId, @RequestBody Brand brandRequest) {
    Brand brand = brandRequest.toBuilder().id(brandId).build();
    return brandService
        .updateBrand(brand)
        .onErrorResume(
            error -> {
              log.error(
                  "Error occured while updating brand by id: {}, excepion :{}",
                  brandId,
                  error.getMessage());
              return Mono.error(new BusinessException("Error occured While updating brand"));
            });
  }
}
