FROM openjdk:17-slim

WORKDIR /app

# Copy everything
COPY . .

# Compile
RUN javac -cp ".:json-20210307.jar" src/com/ecommerce/api/*.java

# Expose port
EXPOSE 8080

# Run
CMD ["java", "-cp", ".:json-20210307.jar:src", "com.ecommerce.api.ProductWebApp"]
