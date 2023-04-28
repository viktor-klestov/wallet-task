FROM node:20-alpine AS build_front
WORKDIR /app
COPY ./frontend/package*.json ./
RUN npm install
COPY ./frontend .
RUN npm run build

FROM gradle:7.4.2-jdk17-alpine AS build_back
COPY --chown=gradle:gradle . /home/gradle/src
COPY --from=build_front /app/build /home/gradle/src/src/main/resources/static/
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build_back /home/gradle/src/build/libs/*.jar /app/wallet-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/wallet-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
