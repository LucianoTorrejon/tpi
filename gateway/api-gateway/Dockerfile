FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -pl gateway/api-gateway -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/gateway/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
