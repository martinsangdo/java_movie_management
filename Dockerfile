# Stage 1: Build app with Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# copy pom.xml and download dependencies (cached)
COPY pom.xml .
RUN mvn dependency:go-offline

# copy source and build jar
COPY src ./src
RUN mvn -DskipTests clean package

# Stage 2: Run app with JDK only
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
