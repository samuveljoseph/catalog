package com.ecommerce.catalog.business.mapper;

import com.ecommerce.catalog.business.model.mongo.category.CategoryMongo;
import com.ecommerce.catalog.business.model.request.category.Category;
import com.ecommerce.catalog.business.util.CommonUtil;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryToCategoryMongoMapper {
  @Mapping(target = "id", source = "id", qualifiedByName = "id")
  @Mapping(target = "metaDescription", source = "metaDescription")
  @Mapping(target = "metaTitle", source = "metaTitle")
  CategoryMongo map(Category category);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  CategoryMongo updateMapper(Category category,@MappingTarget CategoryMongo categoryMongo);

  @Named("id")
  default String mapToId(String id) {
    return CommonUtil.generateUUID();
  }
}
