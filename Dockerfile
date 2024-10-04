# 1단계: 빌드 환경 준비
FROM openjdk:17-alpine AS build
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
# 현재 프로젝트 파일 복사
COPY ${JAR_FILE} ./recode.jar
# gradlew 파일에 권한 부여
RUN chmod +x ./gradlew
# gradlew 파일을 Unix 형식으로 변환 (줄 바꿈 문제 해결)
RUN sed -i 's/\r$//' ./gradlew
RUN ./gradlew build
# 컨테이너가 런타임 시에 사용할 포트 노출
EXPOSE 8081
