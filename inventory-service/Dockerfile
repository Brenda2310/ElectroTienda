FROM eclipse-temurin:17-jdk-alpine
LABEL authors="brend"

WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]