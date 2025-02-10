# Weather Application

날씨 정보를 검색하고 표시하는 Spring Boot 기반 웹 애플리케이션입니다.

## 기술 스택

- Spring Boot 3.3.7
- Spring Batch
- Spring Data JPA
- Thymeleaf
- H2 Database
- Lombok
- Gradle

## 주요 기능

- 도시별 날씨 검색
- 현재 위치 기반 날씨 정보 제공
- 날씨 정보 배치 처리

## 시작하기

### 필수 조건

- JDK 17
- Gradle

### 실행 방법

1. 프로젝트 클론
```bash
git clone https://github.com/KangSeokhyung/weather.git
```

2. 프로젝트 디렉토리로 이동
```bash
cd weather
```

3. 애플리케이션 실행
```bash
./gradlew bootRun
```

4. 브라우저에서 접속
```
http://localhost:8080
```

## 개발 환경 설정 가이드

### 필수 요구사항
1. Java 17 이상
2. PostgreSQL 15 이상
3. DBeaver (또는 다른 데이터베이스 관리 도구)

### 데이터베이스 설정
1. PostgreSQL 설치
   - [PostgreSQL 다운로드 페이지](https://www.postgresql.org/download/)에서 운영체제에 맞는 버전 설치
   - 기본 포트: 5432
   - 기본 계정: postgres

2. 데이터베이스 생성
   ```sql
   CREATE DATABASE weather
       WITH 
       OWNER = postgres
       ENCODING = 'UTF8'
       LC_COLLATE = 'Korean_Korea.949'
       LC_CTYPE = 'Korean_Korea.949'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;
   ```

### 애플리케이션 설정
1. application.properties 확인
   - 데이터베이스 연결 정보가 올바른지 확인
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/weather
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

2. 프로젝트 실행
   - 처음 실행 시 자동으로 테이블이 생성되고 초기 데이터가 입력됩니다
   - 기본 포트: 8080

### 주의사항
- 데이터베이스는 로컬 환경에 종속되므로, 새로운 환경에서 프로젝트를 실행할 때마다 위의 데이터베이스 설정이 필요합니다.
- 초기 데이터는 data.sql 파일을 통해 자동으로 입력됩니다.

## 라이선스

This project is licensed under the MIT License
