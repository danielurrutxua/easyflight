FROM gradle:6.9.0-jdk11 AS builder
COPY . /app
WORKDIR /app
RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim
COPY --from=builder /app/build/libs/ /app
EXPOSE 8080
WORKDIR /app
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","easyflight-0.0.1.jar"]

