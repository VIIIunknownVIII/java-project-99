# Use the Eclipse Temurin Java 20 base image
FROM eclipse-temurin:20-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the pre-built JAR file into the container
COPY app/build/libs/app-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Set the entrypoint to run the application
CMD ["java", "-jar", "/app/app.jar"]
