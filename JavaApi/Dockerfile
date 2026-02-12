FROM eclipse-temurin:17-jdk-alpine

# Set working directory to JavaApi
WORKDIR /app/JavaApi

# Copy JavaApi folder
COPY JavaApi/ .

# Compile
RUN javac -cp ".:json-20210307.jar" src/com/ecommerce/api/*.java

# Expose port
EXPOSE 8080
ENV PORT=8080

# Run
CMD ["java", "-cp", ".:json-20210307.jar:src", "com.ecommerce.api.ProductWebApp"]
