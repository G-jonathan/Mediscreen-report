FROM openjdk:17-jdk-alpine
LABEL authors="jonathan.G"
ADD build/libs/Mediscreen-report-0.0.1-SNAPSHOT.jar Mediscreen-report-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Mediscreen-report-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083