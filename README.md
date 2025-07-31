# Rebook Book Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)

**Rebook 마이크로서비스 아키텍처의 도서 관리 서비스**

도서 검색, 등록, 추천, 리뷰, 북마크 기능을 제공하는 핵심 비즈니스 서비스

</div>

---

## 📋 목차

- [프로젝트 개요](#-프로젝트-개요)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [빠른 시작](#-빠른-시작)
- [API 문서](#-api-문서)
- [개발 가이드](#-개발-가이드)

---

## 📖 프로젝트 개요

**Rebook Book Service**는 Rebook 플랫폼의 핵심 도서 관리 마이크로서비스로, Spring Boot 3.3.13과 Java 17 기반으로 구축되었습니다.

### 🎯 서비스 역할

- 📚 도서 정보의 등록, 조회, 검색 및 관리
- 🔍 외부 API(Naver Books)를 활용한 도서 검색
- 🤖 AI 기반 자동 카테고리 분류 (Gemini AI)
- ⭐ 사용자 리뷰 및 평점 관리
- 🔖 북마크 기능을 통한 개인화 서비스
- 📢 신규 도서 등록 시 실시간 알림 발송

### 📊 핵심 도메인 모델

| 도메인 | 설명 | 주요 속성 |
|--------|------|-----------|
| **Book** | 도서 정보 | ISBN, 제목, 저자, 출판사, 카테고리, 이미지 |
| **BookReview** | 사용자 리뷰 | 평점, 리뷰 내용, 작성자, 작성일 |
| **BookMark** | 북마크 | userId + bookId 복합키, 생성일 |

### 🏗 마이크로서비스 특징

- ✅ **서비스 디스커버리**: Netflix Eureka 클라이언트 통합
- ✅ **중앙 설정 관리**: Spring Cloud Config 서버 연동
- ✅ **외부 API 통합**: Feign 클라이언트 기반 REST 통신
- ✅ **이벤트 기반 메시징**: RabbitMQ를 통한 비동기 알림
- ✅ **분산 캐싱**: Redis 기반 성능 최적화
- ✅ **API 문서화**: SpringDoc OpenAPI 자동 생성
- ✅ **모니터링**: Spring Actuator, Sentry 통합

## ✨ 주요 기능

### 도서 관리
- ✅ 네이버 Books API를 통한 외부 도서 검색
- ✅ 도서 등록 및 조회 (페이징 지원)
- ✅ 키워드 기반 도서 검색
- ✅ 도서 상세 정보 조회

### 개인화
- ✅ 사용자 맞춤 도서 추천
- ✅ AI 기반 자동 카테고리 분류 (Gemini AI)
- ✅ 북마크 기능

### 리뷰 시스템
- ✅ 도서 리뷰 작성 및 조회
- ✅ 평점 관리

### 연동 및 알림
- ✅ 신규 도서 등록 시 RabbitMQ 기반 알림 발송
- ✅ 카테고리별 관심 사용자 알림

## 🛠 기술 스택

### 백엔드 프레임워크
- **Spring Boot** 3.3.13
- **Java** 17
- **Gradle** 빌드 시스템

### 데이터 관리
- **Spring Data JPA** - ORM 및 데이터 접근
- **PostgreSQL** - 메인 데이터베이스
- **Redis** - 캐싱 레이어

### 마이크로서비스
- **Spring Cloud Config** - 중앙 설정 관리
- **Netflix Eureka** - 서비스 디스커버리
- **OpenFeign** - 선언적 HTTP 클라이언트

### 메시징 & 통합
- **Spring AMQP** - RabbitMQ 메시징
- **Naver Books API** - 외부 도서 검색
- **Gemini AI** - 자동 카테고리 분류

### 문서화
- **SpringDoc OpenAPI** - API 문서 자동화

### 모니터링 & 관찰성
- **Spring Actuator** - 헬스 체크 및 메트릭
- **Sentry** - 에러 트래킹
- **Prometheus** - 메트릭 수집

## 🏗 시스템 아키텍처

### 마이크로서비스 패턴
```
┌─────────────────┐
│  API Gateway    │
└────────┬────────┘
         │
    ┌────┴──────┐
    │   Eureka  │ (Service Discovery)
    └────┬──────┘
         │
┌────────┴────────────┐
│  Book Service       │
│  (This Service)     │
├─────────────────────┤
│ - Book Management   │
│ - Review System     │
│ - Bookmark Feature  │
│ - Recommendations   │
└─────────────────────┘
         │
    ┌────┴────┐
    │         │
┌───┴───┐ ┌──┴──────┐
│ Redis │ │  RabbitMQ│
└───────┘ └──────────┘
```

### 외부 연동
- **Naver Books API**: 도서 검색
- **Gemini AI API**: 자동 카테고리 분류
- **User Service**: Feign 클라이언트를 통한 사용자 정보 조회

### 데이터 저장소
- **PostgreSQL**: 도서, 리뷰, 북마크 데이터
- **Redis**: 성능 최적화를 위한 캐싱

## 🚀 빠른 시작

### 📋 필수 요구사항

| 항목 | 버전 | 설명 |
|-----|------|------|
| **Java** | 17+ | OpenJDK 또는 Oracle JDK |
| **Gradle** | 7.0+ | 빌드 도구 |
| **PostgreSQL** | 13+ | 메인 데이터베이스 |
| **Redis** | 6+ | 캐싱 레이어 |
| **RabbitMQ** | 3.9+ | 메시징 브로커 |
---

## 📚 API 문서

### 📖 Swagger UI

애플리케이션 실행 후 브라우저에서 자동 생성된 API 문서를 확인할 수 있습니다.

```
🌐 http://localhost:8080/swagger-ui.html
```

**SpringDoc OpenAPI 3.0**을 사용하여 실시간으로 API 스펙이 자동 생성됩니다.

---

### 🔑 주요 엔드포인트

#### 📚 도서 관리 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| `GET` | `/api/books/external/search` | 네이버 API 도서 검색 | ❌ |
| `POST` | `/api/books` | 신규 도서 등록 | ✅ |
| `GET` | `/api/books` | 도서 목록 조회 (페이징) | ❌ |
| `GET` | `/api/books/search` | 키워드 기반 도서 검색 | ❌ |
| `GET` | `/api/books/{bookId}` | 도서 상세 정보 조회 | ❌ |
| `GET` | `/api/books/recommendations` | 맞춤 추천 도서 조회 | ✅ |

<details>
<summary><b>도서 등록 예제</b></summary>

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "isbn": "9788960773417",
    "title": "클린 코드",
    "author": "로버트 C. 마틴",
    "publisher": "인사이트",
    "category": "컴퓨터/IT"
  }'
```
</details>

---

#### ⭐ 리뷰 관리 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| `POST` | `/api/reviews` | 도서 리뷰 작성 | ✅ |
| `GET` | `/api/reviews/{bookId}` | 특정 도서의 리뷰 조회 | ❌ |

<details>
<summary><b>리뷰 작성 예제</b></summary>

```bash
curl -X POST http://localhost:8080/api/reviews \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "bookId": 123,
    "rating": 5,
    "content": "정말 유익한 책이었습니다!"
  }'
```
</details>

---

#### 🔖 북마크 관리 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| `POST` | `/api/bookmarks` | 북마크 추가 | ✅ |
| `GET` | `/api/bookmarks` | 내 북마크 목록 조회 | ✅ |
| `DELETE` | `/api/bookmarks/{bookmarkId}` | 북마크 삭제 | ✅ |

---


## 💻 개발 가이드

### 📂 프로젝트 구조

```
src/main/java/com/example/rebookbookservice/
├── 📁 controller/              # REST API 엔드포인트 레이어
│   ├── BookController.java           # 도서 관리 API
│   ├── BookReviewController.java     # 리뷰 관리 API
│   └── BookMarkController.java       # 북마크 관리 API
│
├── 📁 service/                 # 비즈니스 로직 레이어
│   ├── BookService.java              # 도서 핵심 비즈니스 로직
│   ├── BookReviewService.java        # 리뷰 비즈니스 로직
│   ├── BookMarkService.java          # 북마크 비즈니스 로직
│   ├── ApiService.java               # 외부 API 통합 서비스
│   ├── BookReader.java               # 도서 조회 전용 서비스
│   └── BookReviewReader.java         # 리뷰 조회 전용 서비스
│
├── 📁 repository/              # 데이터 접근 레이어 (Spring Data JPA)
│   ├── BookRepository.java
│   ├── BookReviewRepository.java
│   └── BookMarkRepository.java
│
├── 📁 feigns/                  # 외부 서비스 통신 레이어
│   ├── NaverClient.java              # Naver Books API 클라이언트
│   └── UserClient.java               # User Service 클라이언트
│
├── 📁 model/                   # DTO 및 요청/응답 객체
│   ├── request/                      # API 요청 객체
│   └── response/                     # API 응답 객체
│
├── 📁 domain/                  # JPA 엔티티 및 도메인 모델
│   ├── Book.java
│   ├── BookReview.java
│   └── BookMark.java
│
├── 📁 common/                  # 공통 유틸리티 및 설정
│   ├── ResponseService.java          # 표준화된 응답 생성
│   ├── GlobalExceptionHandler.java   # 전역 예외 처리
│   └── Result.java                   # 공통 응답 래퍼
│
└── 📁 config/                  # 애플리케이션 설정
    ├── SwaggerConfig.java            # API 문서 설정
    ├── RabbitMQConfig.java           # 메시징 설정
    └── RedisConfig.java              # 캐시 설정
```
