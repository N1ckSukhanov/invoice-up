FROM maven:3.9.6-eclipse-temurin-21-alpine as build
WORKDIR /opt/app
COPY .mvn .mvn
COPY pom.xml mvnw ./

RUN mvn dependency:resolve
COPY ./src ./src
RUN mvn clean
RUN mvn package

FROM maven:3.9.6-eclipse-temurin-21-alpine
WORKDIR /opt/app
COPY ./spring-backend/target/*.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
