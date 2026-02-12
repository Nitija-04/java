# Use Eclipse Temurin (more reliable than openjdk)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy source files and library
COPY src ./src
COPY json-20210307.jar ./json-20210307.jar

# Compile Java files
RUN javac -cp ".:json-20210307.jar" src/com/ecommerce/api/*.java

# Expose port (different platforms use different ports)
EXPOSE 8080
ENV PORT=8080

# Run the application
CMD ["java", "-cp", ".:json-20210307.jar:src", "com.ecommerce.api.ProductWebApp"]
