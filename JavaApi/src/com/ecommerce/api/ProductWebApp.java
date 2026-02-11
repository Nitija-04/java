package com.ecommerce.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Optional;


public class ProductWebApp {
    private final ProductService productService;

    public ProductWebApp() {
        this.productService = new ProductService();
    }

    
    public void start() throws IOException {
        String portEnv = System.getenv("PORT");
int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;
HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);


       
        server.createContext("/", new WebUIHandler());

       
        server.createContext("/api/products", new ProductHandler());
        server.createContext("/api/products/", new ProductByIdHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" Product Management System Started!");
        System.out.println("=".repeat(60));
        System.out.println("\n Open your browser and go to:");
        System.out.println("    http://localhost:8080");
        System.out.println("\n You'll see a user-friendly web interface!");
        System.out.println("\nPress Ctrl+C to stop the server.\n");
    }

   
    class WebUIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = generateHTML();
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, html.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(html.getBytes());
            os.close();
        }

        private String generateHTML() {
            return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Product Management System</title>\n" +
                "    <style>\n" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            min-height: 100vh;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container { max-width: 1200px; margin: 0 auto; }\n" +
                "        .header {\n" +
                "            background: white;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 15px;\n" +
                "            box-shadow: 0 10px 30px rgba(0,0,0,0.2);\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        h1 { color: #667eea; font-size: 2.5em; margin-bottom: 10px; }\n" +
                "        .subtitle { color: #666; font-size: 1.1em; }\n" +
                "        .content { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }\n" +
                "        .card {\n" +
                "            background: white;\n" +
                "            padding: 25px;\n" +
                "            border-radius: 15px;\n" +
                "            box-shadow: 0 10px 30px rgba(0,0,0,0.2);\n" +
                "        }\n" +
                "        .card h2 {\n" +
                "            color: #667eea;\n" +
                "            margin-bottom: 20px;\n" +
                "            border-bottom: 3px solid #667eea;\n" +
                "            padding-bottom: 10px;\n" +
                "        }\n" +
                "        .form-group { margin-bottom: 15px; }\n" +
                "        label { display: block; margin-bottom: 5px; color: #333; font-weight: 600; }\n" +
                "        input, select {\n" +
                "            width: 100%;\n" +
                "            padding: 12px;\n" +
                "            border: 2px solid #e0e0e0;\n" +
                "            border-radius: 8px;\n" +
                "            font-size: 14px;\n" +
                "            transition: border 0.3s;\n" +
                "        }\n" +
                "        input:focus { outline: none; border-color: #667eea; }\n" +
                "        button {\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            color: white;\n" +
                "            padding: 12px 25px;\n" +
                "            border: none;\n" +
                "            border-radius: 8px;\n" +
                "            font-size: 16px;\n" +
                "            font-weight: 600;\n" +
                "            cursor: pointer;\n" +
                "            transition: transform 0.2s;\n" +
                "            width: 100%;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        button:hover { transform: translateY(-2px); }\n" +
                "        button:active { transform: translateY(0); }\n" +
                "        .btn-secondary { background: #6c757d; }\n" +
                "        #result { margin-top: 20px; padding: 15px; border-radius: 8px; display: none; }\n" +
                "        .success { background: #d4edda; border: 2px solid #28a745; color: #155724; }\n" +
                "        .error { background: #f8d7da; border: 2px solid #dc3545; color: #721c24; }\n" +
                "        .product-list { max-height: 500px; overflow-y: auto; }\n" +
                "        .product-item {\n" +
                "            background: #f8f9fa;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            margin-bottom: 10px;\n" +
                "            border-left: 4px solid #667eea;\n" +
                "        }\n" +
                "        .product-item h3 { color: #333; margin-bottom: 8px; }\n" +
                "        .product-item p { color: #666; margin: 4px 0; }\n" +
                "        .badge {\n" +
                "            background: #667eea;\n" +
                "            color: white;\n" +
                "            padding: 4px 10px;\n" +
                "            border-radius: 12px;\n" +
                "            font-size: 12px;\n" +
                "            display: inline-block;\n" +
                "            margin-top: 5px;\n" +
                "        }\n" +
                "        @media (max-width: 768px) { .content { grid-template-columns: 1fr; } }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>🛍️ Product Management System</h1>\n" +
                "            <p class=\"subtitle\">Manage your e-commerce products with ease</p>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <div class=\"card\">\n" +
                "                <h2>➕ Add New Product</h2>\n" +
                "                <form id=\"addForm\">\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label>Product Name *</label>\n" +
                "                        <input type=\"text\" id=\"name\" required placeholder=\"e.g., Wireless Mouse\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label>Description</label>\n" +
                "                        <input type=\"text\" id=\"description\" placeholder=\"Product description\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label>Price (₹) *</label>\n" +
                "                        <input type=\"number\" id=\"price\" step=\"0.01\" required placeholder=\"2500.00\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label>Quantity *</label>\n" +
                "                        <input type=\"number\" id=\"quantity\" required placeholder=\"50\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label>Category *</label>\n" +
                "                        <input type=\"text\" id=\"category\" required placeholder=\"Electronics\">\n" +
                "                    </div>\n" +
                "                    <button type=\"submit\">Add Product</button>\n" +
                "                </form>\n" +
                "                <div id=\"result\"></div>\n" +
                "            </div>\n" +
                "            <div class=\"card\">\n" +
                "                <h2>📦 View Products</h2>\n" +
                "                <div class=\"form-group\">\n" +
                "                    <label>Search by Product ID</label>\n" +
                "                    <input type=\"number\" id=\"searchId\" placeholder=\"Enter product ID\">\n" +
                "                    <button onclick=\"searchById()\" class=\"btn-secondary\">Search</button>\n" +
                "                </div>\n" +
                "                <button onclick=\"loadAllProducts()\">View All Products</button>\n" +
                "                <div id=\"productList\" class=\"product-list\"></div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <script>\n" +
                "        document.getElementById('addForm').addEventListener('submit', async (e) => {\n" +
                "            e.preventDefault();\n" +
                "            const product = {\n" +
                "                name: document.getElementById('name').value,\n" +
                "                description: document.getElementById('description').value,\n" +
                "                price: parseFloat(document.getElementById('price').value),\n" +
                "                quantity: parseInt(document.getElementById('quantity').value),\n" +
                "                category: document.getElementById('category').value\n" +
                "            };\n" +
                "            try {\n" +
                "                const response = await fetch('/api/products', {\n" +
                "                    method: 'POST',\n" +
                "                    headers: { 'Content-Type': 'application/json' },\n" +
                "                    body: JSON.stringify(product)\n" +
                "                });\n" +
                "                if (response.ok) {\n" +
                "                    const data = await response.json();\n" +
                "                    showResult('✅ Product added successfully! ID: ' + data.id, 'success');\n" +
                "                    document.getElementById('addForm').reset();\n" +
                "                    loadAllProducts();\n" +
                "                } else {\n" +
                "                    const error = await response.text();\n" +
                "                    showResult('❌ Error: ' + error, 'error');\n" +
                "                }\n" +
                "            } catch (error) {\n" +
                "                showResult('❌ Error: ' + error.message, 'error');\n" +
                "            }\n" +
                "        });\n" +
                "        function showResult(message, type) {\n" +
                "            const result = document.getElementById('result');\n" +
                "            result.textContent = message;\n" +
                "            result.className = type;\n" +
                "            result.style.display = 'block';\n" +
                "            setTimeout(() => { result.style.display = 'none'; }, 5000);\n" +
                "        }\n" +
                "        async function loadAllProducts() {\n" +
                "            try {\n" +
                "                const response = await fetch('/api/products');\n" +
                "                const products = await response.json();\n" +
                "                displayProducts(products);\n" +
                "            } catch (error) {\n" +
                "                document.getElementById('productList').innerHTML = '<p>Error loading products</p>';\n" +
                "            }\n" +
                "        }\n" +
                "        async function searchById() {\n" +
                "            const id = document.getElementById('searchId').value;\n" +
                "            if (!id) { alert('Please enter a product ID'); return; }\n" +
                "            try {\n" +
                "                const response = await fetch('/api/products/' + id);\n" +
                "                if (response.ok) {\n" +
                "                    const product = await response.json();\n" +
                "                    displayProducts([product]);\n" +
                "                } else {\n" +
                "                    document.getElementById('productList').innerHTML = '<p style=\"color: red;\">❌ Product not found</p>';\n" +
                "                }\n" +
                "            } catch (error) {\n" +
                "                document.getElementById('productList').innerHTML = '<p>Error: ' + error.message + '</p>';\n" +
                "            }\n" +
                "        }\n" +
                "        function displayProducts(products) {\n" +
                "            const list = document.getElementById('productList');\n" +
                "            if (products.length === 0) { list.innerHTML = '<p>No products found</p>'; return; }\n" +
                "            list.innerHTML = products.map(p => `\n" +
                "                <div class=\"product-item\">\n" +
                "                    <h3>${p.name} <span class=\"badge\">ID: ${p.id}</span></h3>\n" +
                "                    <p><strong>Description:</strong> ${p.description}</p>\n" +
                "                    <p><strong>Price:</strong> ₹${p.price.toFixed(2)}</p>\n" +
                "                    <p><strong>Quantity:</strong> ${p.quantity} units</p>\n" +
                "                    <p><strong>Category:</strong> ${p.category}</p>\n" +
                "                </div>\n" +
                "            `).join('');\n" +
                "        }\n" +
                "        window.onload = loadAllProducts;\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        }
    }

    class ProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

            try {
                if ("POST".equals(method)) {
                    handleAddProduct(exchange);
                } else if ("GET".equals(method)) {
                    handleGetAllProducts(exchange);
                } else if ("OPTIONS".equals(method)) {
                    exchange.sendResponseHeaders(204, -1);
                } else {
                    sendResponse(exchange, 405, "Method not allowed");
                }
            } catch (Exception e) {
                sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }

        private void handleAddProduct(HttpExchange exchange) throws IOException {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }

                JSONObject json = new JSONObject(body.toString());

                Product product = new Product();
                product.setName(json.getString("name"));
                product.setDescription(json.optString("description", ""));
                product.setPrice(json.getDouble("price"));
                product.setQuantity(json.getInt("quantity"));
                product.setCategory(json.getString("category"));

                Product savedProduct = productService.addProduct(product);

                JSONObject response = productToJson(savedProduct);
                sendJsonResponse(exchange, 201, response.toString());

            } catch (IllegalArgumentException e) {
                sendResponse(exchange, 400, "Validation error: " + e.getMessage());
            } catch (Exception e) {
                sendResponse(exchange, 400, "Invalid request: " + e.getMessage());
            }
        }

        private void handleGetAllProducts(HttpExchange exchange) throws IOException {
            JSONArray jsonArray = new JSONArray();
            for (Product product : productService.getAllProducts()) {
                jsonArray.put(productToJson(product));
            }
            sendJsonResponse(exchange, 200, jsonArray.toString());
        }
    }

    
    class ProductByIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

            try {
                if ("GET".equals(method)) {
                    handleGetProductById(exchange);
                } else {
                    sendResponse(exchange, 405, "Method not allowed");
                }
            } catch (Exception e) {
                sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }

        private void handleGetProductById(HttpExchange exchange) throws IOException {
            try {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");
                Long id = Long.parseLong(parts[parts.length - 1]);

                Optional<Product> product = productService.getProductById(id);

                if (product.isPresent()) {
                    JSONObject response = productToJson(product.get());
                    sendJsonResponse(exchange, 200, response.toString());
                } else {
                    sendResponse(exchange, 404, "Product not found");
                }

            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "Invalid product ID");
            }
        }
    }

    private JSONObject productToJson(Product product) {
        JSONObject json = new JSONObject();
        json.put("id", product.getId());
        json.put("name", product.getName());
        json.put("description", product.getDescription());
        json.put("price", product.getPrice());
        json.put("quantity", product.getQuantity());
        json.put("category", product.getCategory());
        return json;
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void main(String[] args) {
        try {
            ProductWebApp app = new ProductWebApp();
            app.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}