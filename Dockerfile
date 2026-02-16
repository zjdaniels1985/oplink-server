# Dockerfile for oplink-server
# Note: This expects the JAR to be pre-built (run 'mvn clean package' first)

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create non-root user
RUN groupadd -r oplink && useradd -r -g oplink oplink

# Copy jar from local build
COPY target/*.jar app.jar

# Change ownership
RUN chown -R oplink:oplink /app

# Switch to non-root user
USER oplink

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
