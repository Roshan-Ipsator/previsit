# Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy gradle wrapper and build files
COPY gradlew .
COPY gradlew.bat .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application
RUN chmod +x gradlew && ./gradlew clean bootJar -q

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD java -cp /app/app.jar org.springframework.boot.loader.JarLauncher || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
