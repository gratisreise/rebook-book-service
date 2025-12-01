# 푸쉬: docker buildx build --platform=linux/amd64 -t nooaahh/rebook-book-service --push .
# 빌드: ./gradlew clean build
# 생성: docker build -t nooaahh/rebook-book-service:latest .

FROM eclipse-temurin:17-jdk
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]