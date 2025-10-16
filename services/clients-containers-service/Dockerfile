FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -pl services/clients-containers-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/services/clients-containers-service/target/clients-containers-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
