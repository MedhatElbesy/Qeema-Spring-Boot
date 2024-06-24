FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/order-task-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8088

CMD ["java", "-jar", "/app/app.jar"]
