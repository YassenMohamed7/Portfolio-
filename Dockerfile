# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=65 -XX:InitialRAMPercentage=35 -XX:MaxMetaspaceSize=128m -XX:+ExitOnOutOfMemoryError" \
    SERVER_TOMCAT_THREADS_MAX=50 \
    SERVER_TOMCAT_THREADS_MIN_SPARE=5 \
    SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=5 \
    SPRING_CACHE_CAFFEINE_SPEC=maximumSize=200,expireAfterAccess=600s

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]