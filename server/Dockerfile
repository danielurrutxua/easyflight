FROM openjdk:17-jdk-slim
COPY ./easyflight-0.0.1.jar /app/
EXPOSE 8080
WORKDIR /app
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "easyflight-0.0.1.jar"]


