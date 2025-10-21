FROM maven:4.0.0-rc-4-ibm-semeru-25-noble AS build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests


FROM openjdk:17.0.1-slim
WORKDIR /app

COPY --from=build /build/target/image-processing-api-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]