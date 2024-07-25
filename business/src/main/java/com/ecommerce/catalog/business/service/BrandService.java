package com.ecommerce.catalog.business.service;

import com.ecommerce.catalog.business.model.request.brand.Brand;
import com.ecommerce.catalog.business.util.ApiResponse;
import reactor.core.publisher.Mono;

public interface BrandService {
  Mono<ApiResponse<Brand>> createBrand(Brand brand);

  Mono<ApiResponse<Brand>> updateBrand(Brand brand);

  Mono<ApiResponse<Brand>> fetchBrandById(String brandId);

  Mono<ApiResponse<Brand>> deleteBrandById(String brandId);
}
