# Use stable Java image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built jar
COPY target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]