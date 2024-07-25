package com.ecommerce.catalog.business.model.request.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Brand {
    private String id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String logoUrl;

    private String description;
    private String createdDate;
    private String updatedDate;
}
