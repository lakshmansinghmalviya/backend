# Use a lightweight Java runtime
FROM eclipse-temurin:17-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (caching layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application JAR
RUN ./mvnw clean package -DskipTests

# -----------------------------
# Run stage: minimal image
# -----------------------------
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port (default Spring Boot is 8080)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
