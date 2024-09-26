# 1단계: 빌드
FROM openjdk:17-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

# 2단계: 실행
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/recode.jar .
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8081
CMD ["java", "-jar", "recode.jar"]