FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/coffee-store-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} coffee-store-service.jar
ENTRYPOINT ["java","-jar","/coffee-store-service.jar"]