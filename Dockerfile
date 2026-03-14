# -------- Build stage --------
FROM maven:3.9.9-eclipse-temurin-24 AS build

WORKDIR /build

# Copy project
COPY pom.xml .
COPY src ./src

# Build jar
RUN mvn -B package --no-transfer-progress

# -------- Runtime stage --------
FROM eclipse-temurin:24-jre

WORKDIR /app

# Copy built jar
COPY --from=build /build/target/*jar-with-dependencies.jar app.jar

# Copy config
COPY config/ ./config/

ENV PORT=8080

CMD ["java", "-jar", "app.jar"]