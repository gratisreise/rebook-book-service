# 푸쉬: docker buildx build --platform=linux/amd64 -t nooaahh/rebook-book-service --push .
# 생성: docker build -t nooaahh/rebook-book-service:latest .

# 1. 빌드 환경
FROM gradle:8.14.2-jdk17 AS builder
WORKDIR /app

# 1-1. gradle wrapper, 설정 파일, 의존성 정보만 먼저 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 1-2. 의존성 미리 다운로드 (소스 변경과 무관한 레이어)
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# 1-3. 실제 소스 복사 (이 레이어부터 소스 변경 영향)
COPY src src

# 1-4. 테스트 및 빌드
RUN ./gradlew bootJar --no-daemon

# 2. 실행 환경
FROM openjdk:17-slim AS run
WORKDIR /app

#jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]