# Java base 24
FROM eclipse-temurin:24-jre

# Create workdir
WORKDIR /app

# Copy jar and config
COPY target/LesVeilleursDiscordBot-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY config/ ./config/

# Cloud Run port
ENV PORT=8080

# Launch bot
CMD ["java", "-jar", "app.jar"]
