package com.ecommerce.catalog.business.model.request.category;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Category {
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
    private String image;
    private String bannerImage;
    private String thumbnail;
    private String urlSlug;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;

    @NotNull(message = "Status is mandatory")
    private boolean status;
    private int sortOrder;
    private String createdDate;
    private String updatedDate;
}
