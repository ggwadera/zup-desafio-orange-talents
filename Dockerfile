FROM openjdk:15-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]