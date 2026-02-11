FROM openjdk:17-slim

WORKDIR /app

# Copy source files
COPY src ./src
COPY json-20210307.jar ./

# Compile
RUN javac -cp ".:json-20210307.jar" src/com/ecommerce/api/*.java

# Expose port
EXPOSE 8080

# Run
CMD ["java", "-cp", ".:json-20210307.jar:src", "com.ecommerce.api.ProductWebApp"]
