Here's a comprehensive README file for your application:

```markdown
# Product Management REST API - Java Backend Application

A Java-based RESTful API for managing e-commerce products with an embedded web interface for easy testing and demonstration.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Implementation Details](#implementation-details)
- [Testing the Application](#testing-the-application)
- [Troubleshooting](#troubleshooting)

---

## Overview

This is a **backend Java application** that implements a RESTful API for managing a collection of products in an e-commerce system. The application uses in-memory storage (ArrayList) and includes an embedded web interface for easy interaction with the API.

**Technology Stack:**
- **Language:** Java 8+
- **Server:** Java HttpServer (com.sun.net.httpserver)
- **Data Format:** JSON
- **Storage:** In-memory ArrayList
- **External Libraries:** org.json (for JSON parsing)

---

## Features

✅ **RESTful API Design**
- Add new products
- Retrieve products by ID
- Retrieve all products

✅ **Input Validation**
- Required field validation
- Data type validation
- Business rule enforcement

✅ **Web Interface**
- User-friendly HTML/CSS/JavaScript interface
- Form-based product creation
- Visual product display
- Search functionality

✅ **Error Handling**
- Validation errors (400 Bad Request)
- Not found errors (404 Not Found)
- Server errors (500 Internal Server Error)

---

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
   - Check: `java -version` and `javac -version`
   - Download: https://www.oracle.com/java/technologies/downloads/ or https://adoptium.net/

2. **JSON Library**
   - org.json (json-20210307.jar)
   - Download from: https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar

---

## Project Structure

```
ProductWebApp/
├── src/
│   └── com/
│       └── ecommerce/
│           └── api/
│               ├── Product.java          # Product model/entity class
│               ├── ProductService.java   # Business logic and data management
│               └── ProductWebApp.java    # HTTP server and API endpoints
├── json-20210307.jar                     # JSON library dependency
└── README.md                             # This file
```

---

## Installation & Setup

### Step 1: Create Project Directory

```bash
mkdir ProductWebApp
cd ProductWebApp
```

### Step 2: Create Package Structure

```bash
mkdir -p src/com/ecommerce/api
```

### Step 3: Add Java Files

Copy the three Java files into `src/com/ecommerce/api/`:
- Product.java
- ProductService.java
- ProductWebApp.java

### Step 4: Download JSON Library

**Mac/Linux:**
```bash
wget https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar
```

**Windows (PowerShell):**
```powershell
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar" -OutFile "json-20210307.jar"
```

**Or download manually** from the URL above and save it in the `ProductWebApp` folder.

---

## Running the Application

### Compile the Application

**Mac/Linux:**
```bash
javac -cp ".:json-20210307.jar" src/com/ecommerce/api/*.java
```

**Windows:**
```bash
javac -cp ".;json-20210307.jar" src\com\ecommerce\api\*.java
```

### Run the Application

**Mac/Linux:**
```bash
java -cp ".:json-20210307.jar:src" com.ecommerce.api.ProductWebApp
```

**Windows:**
```bash
java -cp ".;json-20210307.jar;src" com.ecommerce.api.ProductWebApp
```

### Expected Output

```
============================================================
Product Management System Started!
============================================================

 Open your browser and go to:
    http://localhost:8080

 You'll see a user-friendly web interface!

Press Ctrl+C to stop the server.
```

---

## API Endpoints

### Base URL
```
http://localhost:8080
```

### Endpoint 1: Add New Product

**Endpoint:** `POST /api/products`

**Description:** Creates a new product in the system.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Gaming Mouse",
  "description": "RGB wireless gaming mouse",
  "price": 2500.00,
  "quantity": 30,
  "category": "Electronics"
}
```

**Required Fields:**
- `name` (String) - Product name
- `price` (Number) - Product price (must be ≥ 0)
- `quantity` (Integer) - Available quantity (must be ≥ 0)
- `category` (String) - Product category

**Optional Fields:**
- `description` (String) - Product description

**Success Response (201 Created):**
```json
{
  "id": 4,
  "name": "Gaming Mouse",
  "description": "RGB wireless gaming mouse",
  "price": 2500.0,
  "quantity": 30,
  "category": "Electronics"
}
```

**Error Response (400 Bad Request):**
```
Validation error: Product name is required
```

**Example using curl:**
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Mouse",
    "description": "RGB wireless gaming mouse",
    "price": 2500.00,
    "quantity": 30,
    "category": "Electronics"
  }'
```

---

### Endpoint 2: Get Product by ID

**Endpoint:** `GET /api/products/{id}`

**Description:** Retrieves a single product by its ID.

**URL Parameters:**
- `id` (Long) - Product ID

**Success Response (200 OK):**
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance gaming laptop",
  "price": 75000.0,
  "quantity": 10,
  "category": "Electronics"
}
```

**Error Response (404 Not Found):**
```
Product not found
```

**Error Response (400 Bad Request):**
```
Invalid product ID
```

**Example using curl:**
```bash
curl http://localhost:8080/api/products/1
```

---

### Endpoint 3: Get All Products

**Endpoint:** `GET /api/products`

**Description:** Retrieves all products in the system.

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance gaming laptop",
    "price": 75000.0,
    "quantity": 10,
    "category": "Electronics"
  },
  {
    "id": 2,
    "name": "Smartphone",
    "description": "Latest 5G smartphone",
    "price": 45000.0,
    "quantity": 25,
    "category": "Electronics"
  },
  {
    "id": 3,
    "name": "Coffee Maker",
    "description": "Automatic coffee brewing machine",
    "price": 5500.0,
    "quantity": 15,
    "category": "Home Appliances"
  }
]
```

**Example using curl:**
```bash
curl http://localhost:8080/api/products
```

---

### Endpoint 4: Web Interface (Bonus)

**Endpoint:** `GET /`

**Description:** Serves the web-based user interface for interacting with the API.

**Usage:** Open http://localhost:8080 in your browser.

---

## Implementation Details

### Architecture

The application follows a **3-layer architecture**:

1. **Model Layer** (`Product.java`)
   - Represents the product entity
   - Contains getters/setters for all fields
   - Provides toString() method for debugging

2. **Service Layer** (`ProductService.java`)
   - Manages business logic
   - Handles data storage (ArrayList)
   - Performs input validation
   - Generates unique IDs using AtomicLong

3. **Controller Layer** (`ProductWebApp.java`)
   - Handles HTTP requests/responses
   - Routes requests to appropriate handlers
   - Converts between JSON and Java objects
   - Serves web interface

### Data Storage

**In-Memory Storage:**
- Uses `ArrayList<Product>` to store products
- Data persists only during application runtime
- Resets when application restarts
- Thread-safe ID generation using `AtomicLong`

**Pre-loaded Sample Data:**
The application initializes with 3 sample products:
1. Laptop (₹75,000)
2. Smartphone (₹45,000)
3. Coffee Maker (₹5,500)

### Input Validation

The `ProductService` validates all product data before storage:

| Field | Validation Rule |
|-------|----------------|
| Product object | Cannot be null |
| Name | Required, cannot be empty or whitespace |
| Price | Required, must be ≥ 0 |
| Quantity | Required, must be ≥ 0 |
| Category | Required, cannot be empty or whitespace |
| Description | Optional |

### ID Generation

- IDs are auto-generated using `AtomicLong`
- Starts from 1 and increments for each new product
- Thread-safe implementation
- Cannot be manually set during creation

### HTTP Status Codes

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 OK | Success | GET requests successful |
| 201 Created | Resource created | POST request successful |
| 400 Bad Request | Client error | Validation failed or invalid input |
| 404 Not Found | Resource not found | Product ID doesn't exist |
| 405 Method Not Allowed | Wrong HTTP method | Using POST on GET endpoint, etc. |
| 500 Internal Server Error | Server error | Unexpected server-side error |

### JSON Library

The application uses **org.json** library for JSON processing:
- `JSONObject` - For single product JSON
- `JSONArray` - For multiple products JSON
- Handles serialization/deserialization

---

## Testing the Application

### Method 1: Web Interface (Easiest)

1. Start the application
2. Open http://localhost:8080 in your browser
3. Use the web forms to:
   - Add products (left panel)
   - View all products (right panel)
   - Search by ID (right panel)

### Method 2: Using curl (Command Line)

**Add a product:**
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Keyboard","description":"Mechanical keyboard","price":3500,"quantity":20,"category":"Electronics"}'
```

**Get all products:**
```bash
curl http://localhost:8080/api/products
```

**Get product by ID:**
```bash
curl http://localhost:8080/api/products/1
```

### Method 3: Using Postman

1. Open Postman
2. Create requests for each endpoint
3. Set appropriate HTTP methods and headers
4. Add JSON body for POST requests

---

## Troubleshooting

### Issue: "javac: command not found" or "java: command not found"

**Solution:** Java JDK is not installed or not in PATH
- Install JDK from https://adoptium.net/
- Add Java to your system PATH
- Restart terminal

### Issue: "package org.json does not exist"

**Solution:** JSON library not found
- Ensure `json-20210307.jar` is in the project root folder
- Check the `-cp` classpath in compile command
- Verify the filename matches exactly

### Issue: "Could not find or load main class"

**Solution:** Incorrect classpath or package structure
- Ensure files are in `src/com/ecommerce/api/` folder
- Verify package declaration matches folder structure
- Check the `-cp` parameter includes `src` directory

### Issue: "Port 8080 already in use"

**Solution:** Another application is using port 8080
- Stop the other application
- Or change port in `ProductWebApp.java`:
  ```java
  HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
  ```
- Then access at http://localhost:8081

### Issue: Browser shows blank page

**Solution:**
- Check terminal for error messages
- Ensure server is running (don't close terminal)
- Try refreshing the browser (Ctrl+F5)
- Clear browser cache

### Issue: "Product name is required" error

**Solution:** Validation failed
- Ensure all required fields are provided
- Check field names match exactly (case-sensitive)
- Verify data types (price as number, not string)

---

## Stopping the Application

Press **Ctrl+C** in the terminal where the application is running.

---

## Future Enhancements

Potential improvements for production use:

- ✅ Add UPDATE (PUT/PATCH) and DELETE endpoints
- ✅ Implement database persistence (MySQL, PostgreSQL, MongoDB)
- ✅ Add authentication and authorization
- ✅ Implement pagination for large datasets
- ✅ Add search and filtering capabilities
- ✅ Use Spring Boot framework for enterprise features
- ✅ Add unit and integration tests
- ✅ Implement logging framework
- ✅ Add API documentation (Swagger/OpenAPI)
- ✅ Dockerize the application

---

## Notes

- This is a **learning project** demonstrating RESTful API concepts in Java
- Not recommended for production use without proper database and security
- Designed for educational purposes and portfolio demonstration
- Data is stored in memory and will be lost on restart

---

## Author

Created as a backend REST API project for learning Java web development.

---

## License

This project is for educational purposes.
```

This README provides comprehensive documentation covering all aspects of your application! Save it as `README.md` in your project folder.