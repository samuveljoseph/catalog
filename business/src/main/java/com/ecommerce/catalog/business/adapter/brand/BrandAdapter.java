package com.ecommerce.catalog.business.adapter.brand;

import com.ecommerce.catalog.business.model.mongo.brand.BrandMongo;
import reactor.core.publisher.Mono;

public interface BrandAdapter {
  Mono<BrandMongo> createBrand(BrandMongo brandMongo);

  Mono<BrandMongo> fetchBrandById(String brandId);

  Mono<BrandMongo> deleteBrandById(String brandId);

  Mono<BrandMongo> updateBrandById(BrandMongo brandMongo);
}
