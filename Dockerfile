FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/mosquito-service-1.0-SNAPSHOT-all.jar /app/mosquito-service-1.0-SNAPSHOT-all.jar
CMD ["java", "-jar", "mosquito-service-1.0-SNAPSHOT-all.jar"]