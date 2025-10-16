FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -pl services/requests-routes-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/services/requests-routes-service/target/requests-routes-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
