
FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.8

RUN apt-get update && apt-get install -yq make unzip

WORKDIR /backend

COPY ./app /backend

RUN chmod +x gradlew

RUN ./gradlew --no-daemon build

EXPOSE 8080

CMD java -jar build/libs/app-0.0.1-SNAPSHOT.jar
=======
FROM openjdk:21-jdk-slim

WORKDIR /app

# Обновите путь, если .jar файл имеет другое имя
COPY build/libs/app-0.0.1-SNAPSHOT.jar application.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]
