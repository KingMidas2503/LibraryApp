FROM maven:3.9.8
COPY target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]