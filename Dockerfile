FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./app /app

RUN chmod +x gradlew

RUN ./gradlew build

CMD ["./build/install/app/bin/app"]
