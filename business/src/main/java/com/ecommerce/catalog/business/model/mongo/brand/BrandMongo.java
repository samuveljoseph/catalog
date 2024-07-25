package com.ecommerce.catalog.business.model.mongo.brand;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brands")
@Getter
@Setter
public class BrandMongo {
    @Id
    private String id;

    private String name;

    private String logoUrl;

    private String description;
    private String createdDate;
    private String updatedDate;

}
