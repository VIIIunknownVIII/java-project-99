# Set a build argument to conditionally run the Gradle task
ARG SKIP_GRADLE_TASK=false

# Use the base Gradle image with JDK 20
FROM gradle:8.3.0-jdk20

# Set the working directory in the container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Run the Gradle task only if SKIP_GRADLE_TASK is false
RUN if [ "$SKIP_GRADLE_TASK" = "false" ]; then ./gradlew installDist; else echo "Skipping Gradle task"; fi

# Specify the command to start the application
CMD ./build/install/app/bin/app

# Expose the port for the application (e.g., 8090)
EXPOSE 8090