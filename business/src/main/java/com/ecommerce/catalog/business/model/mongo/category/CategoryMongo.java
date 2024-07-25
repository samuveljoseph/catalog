package com.ecommerce.catalog.business.model.mongo.category;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Category")
@Builder(toBuilder = true)
@Getter
@Setter
public class CategoryMongo{
    @Id
    private String id;
    private String name;
    private String description;
    private String image;
    private String bannerImage;
    private String thumbnail;
    private String urlSlug;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private boolean status;
    private int sortOrder;
    private String createdDate;
    private String updatedDate;
}
