# ================================
# 1. Build Stage
# ================================
FROM gradle:8.8-jdk21 AS builder

WORKDIR /app

# Copy Gradle wrapper & project files first for caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies only (cached if build.gradle unchanged)
RUN ./gradlew dependencies --no-daemon --parallel || true

# Copy source code
COPY src ./src

# Build Spring Boot fat jar (skip tests, parallel)
RUN ./gradlew bootJar -x test --no-daemon --parallel

# ================================
# 2. Runtime Stage
# ================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Create a low-privileged user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]