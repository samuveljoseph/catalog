package com.ecommerce.catalog.business.repository;

import com.ecommerce.catalog.business.Exception.BusinessException;
import com.ecommerce.catalog.business.adapter.brand.BrandAdapter;
import com.ecommerce.catalog.business.mapper.BrandMongoToBrandMapper;
import com.ecommerce.catalog.business.mapper.BrandToBrandMongoMapper;
import com.ecommerce.catalog.business.model.mongo.brand.BrandMongo;
import com.ecommerce.catalog.business.model.request.brand.Brand;
import com.ecommerce.catalog.business.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class BrandRepo {
  @Autowired BrandAdapter brandAdapter;

  public Mono<Brand> createBrand(Brand brand) {
    BrandMongo brandMongo = Mappers.getMapper(BrandToBrandMongoMapper.class).map(brand, null);
    String currentDateTime= CommonUtil.getCurrentDateTime();
    brandMongo.setCreatedDate(currentDateTime);
    brandMongo.setUpdatedDate(currentDateTime);
    return brandAdapter
        .createBrand(brandMongo)
        .map(brandMongo1 -> Mappers.getMapper(BrandMongoToBrandMapper.class).map(brandMongo1))
        .doOnError(
            throwable -> Mono.error(new BusinessException("Error occured While creating brand")));
  }

  public Mono<Brand> fetchBrandById(String brandId) {
    return brandAdapter
        .fetchBrandById(brandId)
        .map(brandMongo -> Mappers.getMapper(BrandMongoToBrandMapper.class).map(brandMongo))
        .switchIfEmpty(Mono.empty())
        .doOnError(
            throwable -> Mono.error(new BusinessException("Error occure While fetching brand")));
  }

  public Mono<BrandMongo> deleteBrandById(String brandId) {
    return brandAdapter
        .deleteBrandById(brandId)
        .switchIfEmpty(Mono.empty())
        .doOnError(
            throwable -> Mono.error(new BusinessException(("Error occured While deleting brand"))));
  }

  public Mono<Brand> updateBrandById(Brand brand) {
    return brandAdapter
        .fetchBrandById(brand.getId())
        .switchIfEmpty(Mono.empty())
        .flatMap(
            brandMongo ->
                getUpdatedCategoryMongo(brandMongo, brand)
                    .flatMap(brandMongo1 -> brandAdapter.updateBrandById(brandMongo1)))
        //        .updateBrandById(brandMongo)
        .map(brandMongo1 -> Mappers.getMapper(BrandMongoToBrandMapper.class).map(brandMongo1))
        .doOnError(throwable -> Mono.error(throwable));
  }

  private Mono<BrandMongo> getUpdatedCategoryMongo(BrandMongo brandMongo, Brand brand) {
    BrandMongo updatedBrand =
        Mappers.getMapper(BrandToBrandMongoMapper.class).updateMapper(brand, brandMongo);
    updatedBrand.setId(brandMongo.getId());
    updatedBrand.setCreatedDate(brandMongo.getCreatedDate());
    updatedBrand.setUpdatedDate(CommonUtil.getCurrentDateTime());
    return Mono.just(updatedBrand);
  }
}
