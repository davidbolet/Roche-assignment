FROM openjdk:11.0.3-jdk-slim-stretch as build

USER nobody
COPY ./prod/assignment.jar ./assignment.jar
EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar","/assignment.jar"]