FROM adoptopenjdk/openjdk11:ubi
ADD /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080