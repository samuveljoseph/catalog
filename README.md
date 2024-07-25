# Catalog Module

## Overview

The Catalog Module is a microservice for managing e-commerce catalog data, including categories, brands, products, and SKUs. It provides RESTful APIs for CRUD operations and integrates with MongoDB for data storage.

## Features

- **Categories**: Manage product categories.
- **Brands**: Handle brand information.
- **Products**: Organize product details.
- **SKUs**: Manage stock-keeping units.

## Installation

### Prerequisites

- Java 17
- Spring Boot
- MongoDB
- Maven or Gradle

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/samuveljoseph/catalog.git
Usage
### API Endpoints

### Category Endpoints

- Create Category: POST /api/categories    
- Get Category by ID: GET /api/categories/{id}
- Update Category: PUT /api/categories/{id}
- Delete Category: DELETE /api/categories/{id}

### Brand EndPoints
   
- Create Brand: Add a new brand to the system: POST /api/brands                             
- Get Brand by ID: Retrieve a specific brand using its ID: GET /api/brands/{id}
- Get All Brands: Retrieve a list of all brands: GET /api/brands
- Update Brand: Modify details of an existing brand: PUT /api/brands/{id}
- Delete Brand: Remove a brand from the system: DELETE /api/brands/{id}

### Product APIs

- Create Product: Add a new product to the catalog: POST /api/products
- Get Product by ID: Retrieve details of a specific product: GET /api/products/{id}
- Get All Products: Retrieve a list of all products, possibly with pagination and filters (e.g., by category, price range): GET /api/products
- Update Product: Modify details of an existing product: PUT /api/products/{id}
- Delete Product: Remove a product from the catalog: DELETE /api/products/{id}
- Search Products: Allow searching for products by name, description, category, or other attributes: GET /api/products/search
- Get Products by Brand: Retrieve all products associated with a specific brand.
- Get Products by Category: Retrieve all products associated with a specific category.

### SKU (Stock Keeping Unit) APIs

- Create SKU: Add a new SKU for a product: POST /api/skus
- Get SKU by ID: Retrieve details of a specific SKU: GET /api/skus/{id}
- Get All SKUs for a Product: Retrieve a list of all SKUs associated with a specific product:GET /api/products/{productId}/skus
- Update SKU: Modify details of an existing SKU (e.g., stock level, price-): PUT /api/skus/{id}
- Delete SKU: Remove an SKU from the system: DELETE /api/skus/{id}
- Update Stock Level: Specific endpoint to update the stock level of an SKU: PATCH /api/skus/{id}/stock
