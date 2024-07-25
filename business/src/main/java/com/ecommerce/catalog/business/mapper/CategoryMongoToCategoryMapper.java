package com.ecommerce.catalog.business.mapper;

import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import com.ecommerce.catalog.business.model.request.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CategoryMongoToCategoryMapper {
  @Mapping(target = "id", source = "id")
  @Mapping(target = "createdDate", source = "createdDate")
  @Mapping(target = "updatedDate", source = "updatedDate")
  Category map(CategoryMongo category);
}
