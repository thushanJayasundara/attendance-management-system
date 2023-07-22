FROM openjdk:8
WORKDIR /app
ADD target/carder-platform.jar carder-platform.jar
ENTRYPOINT ["java", "-jar", "carder-platform.jar"]