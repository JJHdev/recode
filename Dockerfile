# 1단계: 빌드 환경 준비
FROM openjdk:17-alpine AS build
WORKDIR /app
# 현재 프로젝트 파일 복사
COPY . .
# gradlew 파일에 권한 부여
RUN chmod +x ./gradlew
# gradlew 파일을 Unix 형식으로 변환 (줄 바꿈 문제 해결)
RUN sed -i 's/\r$//' ./gradlew
RUN ./gradlew build
# 컨테이너가 런타임 시에 사용할 포트 노출
EXPOSE 80

# 2단계: 애플리케이션 실행 환경 / 멀티스테이징으로 1단계에서 만든 /app/build/libs/recode.jar 을 /app에 하나만 만들고 전부 이미지에 불포함 시킨다.
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/recode.jar .
CMD ["java", "-jar", "recode.jar"]