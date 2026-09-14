# ---- Build Stage ----
FROM eclipse-temurin:21-jdk-alpine AS builder
LABEL authors="abdi"

WORKDIR /app

COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts ./
COPY gradle/ gradle/

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew bootJar --no-daemon


# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/app.jar

RUN chown appuser:appgroup /app/app.jar
USER appuser

EXPOSE 8181

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]