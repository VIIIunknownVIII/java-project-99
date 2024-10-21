FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./app /app

RUN ./gradlew bootJar

CMD ["java", "-jar", "./build/libs/app-0.0.1-SNAPSHOT.jar"]
