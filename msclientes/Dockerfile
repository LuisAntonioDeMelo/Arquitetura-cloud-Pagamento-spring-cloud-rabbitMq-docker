FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build ./app/target/*.jar app.jar

ARG EUREKA_SERVER=localhost

ENTRYPOINT java -jar app.jar
