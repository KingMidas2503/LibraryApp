FROM maven:4.0.0
COPY target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]