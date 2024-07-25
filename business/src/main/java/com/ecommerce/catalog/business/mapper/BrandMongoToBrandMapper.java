package com.ecommerce.catalog.business.mapper;

import com.ecommerce.catalog.business.model.mongo.brand.BrandMongo;
import com.ecommerce.catalog.business.model.request.brand.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BrandMongoToBrandMapper {
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  Brand map(BrandMongo brandMongo);
}
