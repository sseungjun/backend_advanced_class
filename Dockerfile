# 1. Java 런타임이 있는 베이스 이미지 사용
FROM openjdk:17-jdk-slim

# 2. JAR 파일을 컨테이너에 복사
COPY build/libs/*.jar app.jar

# 3. 실행 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
