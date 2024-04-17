FROM amazoncorretto:21-alpine-jdk as build
WORKDIR /opt/app
COPY .mvn .mvn
COPY pom.xml mvnw ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM amazoncorretto:21-alpine-jdk
WORKDIR /opt/app
COPY ./target/*.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]


