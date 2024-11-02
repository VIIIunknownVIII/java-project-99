# Используем базовый образ с JDK 17
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы сборки Gradle и зависимости
COPY app/build.gradle app/settings.gradle app/gradle.properties /app/
COPY app/gradlew /app/gradlew
COPY app/gradle /app/gradle

# Устанавливаем права для выполнения Gradle wrapper
RUN chmod +x gradlew

# Скачиваем зависимости, чтобы использовать кэш Docker, если зависимости не изменились
RUN ./gradlew dependencies || return 0

# Копируем остальную часть проекта
COPY app /app

# Устанавливаем переменные окружения для Sentry
ARG SENTRY_AUTH_TOKEN
ENV SENTRY_AUTH_TOKEN=$SENTRY_AUTH_TOKEN

# Сборка JAR файла с использованием Gradle
RUN ./gradlew bootJar --no-daemon

# Открываем порт (если необходимо)
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "./build/libs/app-0.0.1-SNAPSHOT.jar"]
