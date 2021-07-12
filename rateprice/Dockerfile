FROM adoptopenjdk/openjdk11:latest
WORKDIR  /
MAINTAINER rateprice
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} rateprice.jar
ENTRYPOINT ["java","-jar","rateprice.jar"]