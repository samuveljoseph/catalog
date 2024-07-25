package com.ecommerce.catalog.business.mapper;

import com.ecommerce.catalog.business.model.mongo.brand.BrandMongo;
import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import com.ecommerce.catalog.business.model.request.brand.Brand;
import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.util.CommonUtil;
import org.mapstruct.*;

@Mapper
public interface BrandToBrandMongoMapper {
  @Mapping(target = "id", source = "id", qualifiedByName = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  BrandMongo map(Brand brand, @Context BrandMongo brandMongo);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BrandMongo updateMapper(Brand brand, @MappingTarget BrandMongo brandMongo);

  @Named("id")
  default String mapToId(String id) {
    return CommonUtil.generateUUID();
  }
}
