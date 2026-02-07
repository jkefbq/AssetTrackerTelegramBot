FROM maven:4.0.0-rc-5-sapmachine-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/src/main/resources/static/ai-advice-prompt.txt ./src/main/resources/static/ai-advice-prompt.txt
ENTRYPOINT ["java", "-jar", "app.jar"]