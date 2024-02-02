FROM openjdk:21-jdk
WORKDIR /app
EXPOSE 8080 8443
ARG JAR_FILE=./build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]