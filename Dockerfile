FROM openjdk:11.0.3-jdk-slim-stretch as build

USER nobody
COPY ./build/libs/assignment.jar ./assignment.jar
EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar","/assignment.jar"]