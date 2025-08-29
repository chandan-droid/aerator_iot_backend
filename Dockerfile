# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn

# Copy the source code
COPY src ./src

# Give execute permission for Maven wrapper
RUN chmod +x ./mvnw

# Build the jar file
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/AeratorIoTBackend-0.0.1-SNAPSHOT.jar"]
