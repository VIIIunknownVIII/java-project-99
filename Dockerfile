FROM openjdk:20-jdk
WORKDIR /app
COPY app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
