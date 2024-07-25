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
API Endpoints

Category Endpoints

Create Category: POST /api/categories
Get Category by ID: GET /api/categories/{id}
Update Category: PUT /api/categories/{id}
Delete Category: DELETE /api/categories/{id}

Brand Endpoints

Create Brand: POST /api/brands
Get Brand by ID: GET /api/brands/{id}
Update Brand: PUT /api/brands/{id}
Delete Brand: DELETE /api/brands/{id}

Product Endpoints

Create Product: POST /api/products
Get Product by ID: GET /api/products/{id}
Update Product: PUT /api/products/{id}
Delete Product: DELETE /api/products/{id}

SKU Endpoints

Create SKU: POST /api/skus
Get SKU by ID: GET /api/skus/{id}
Update SKU: PUT /api/skus/{id}
Delete SKU: DELETE /api/skus/{id}
