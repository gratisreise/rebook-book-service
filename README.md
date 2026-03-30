# Rebook Book Service

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)

Rebook 플랫폼의 도서 관리 마이크로서비스입니다. 도서 검색, 등록, AI 기반 자동 카테고리 분류, 이미지 검색, 리뷰, 북마크, 추천 기능을 제공합니다.

## 목차

- [아키텍처](#아키텍처)
- [기능](#기능)
- [기술 스택](#기술-스택)
- [API 문서](#api-문서)
- [프로젝트 구조](#프로젝트-구조)

---

## 아키텍처

- AI 이미지 도서추출
  ![이미지로검색](https://diagrams-noaahh.s3.ap-northeast-2.amazonaws.com/book_searchbyimage.png)
- 도서등록 

---

## 기능

### AI 기능

| 기능 | 설명 | 활용 |
|------|------|------|
| 자동 카테고리 분류 | 도서 등록 시 제목 기반 자동 분류 | Gemini AI |
| 이미지 도서 검색 | 표지 이미지에서 ISBN 추출 후 검색 | Gemini AI + Naver Books API |

### 주요 기능

- **도서 검색**: 키워드 기반 내부 검색 및 Naver Books API 연동 외부 검색
- **도서 등록**: AI 자동 카테고리 분류, 신규 도서 알림 발행 (Outbox 패턴)
- **북마크**: 도서 북마크 토글 및 북마크 목록 조회
- **리뷰**: 도서 리뷰 CRUD 및 평점 관리
- **추천**: 사용자 관심 카테고리 기반 개인화 도서 추천
- **알림**: 신규 도서 등록 시 관심 카테고리 사용자에게 RabbitMQ 알림 발행

---

## 기술 스택

### Language & Framework
- **Java 17**, **Spring Boot 3.3**, **Spring Cloud 2023.0.5**

### Database
- **PostgreSQL**, **QueryDSL 7.1**, **Spring Data JPA**

### Messaging
- **RabbitMQ** (AMQP) — 도서 등록 알림 메시지 비동기 처리 (Outbox 패턴)

### AI & External API
- **Google Gemini** (`gemini-2.5-flash`) — 도서 카테고리 자동 분류, 이미지 ISBN 추출
- **Naver Books API** — 외부 도서 검색

### Cloud & Infra
- **Spring Cloud Config** — 중앙 설정 관리
- **Eureka Client** — 서비스 디스커버리
- **OpenFeign** — 서비스 간 통신 (user-service)
- **AWS S3** — 이미지 스토리지

### Monitoring & Docs
- **Actuator**, **Prometheus**, **Sentry**
- **SpringDoc OpenAPI** (Swagger UI)

### Build & Deploy
- **Gradle**, **Docker**, **GitHub Actions** (CI/CD)

---

## API 문서

Apidog에서 확인하실 수 있습니다:

```
https://x6wq8qo61i.apidog.io/
```

### Books

| Method | Endpoint | 설명 |
|--------|----------|------|
| `GET` | `/api/books` | 도서 목록 조회 (페이지네이션) |
| `GET` | `/api/books/{bookId}` | 도서 상세 조회 (북마크 여부 포함) |
| `GET` | `/api/books/search?keyword=` | 키워드 도서 검색 |
| `GET` | `/api/books/external/search?keyword=` | Naver Books 외부 검색 |
| `POST` | `/api/books` | 도서 등록 (AI 자동 카테고리 분류) |
| `POST` | `/api/books/search/image` | 이미지로 도서 검색 (Gemini ISBN 추출) |
| `GET` | `/api/books/recommendations` | 개인화 도서 추천 |

### Bookmarks

| Method | Endpoint | 설명 |
|--------|----------|------|
| `POST` | `/api/books/{bookId}/marks` | 북마크 토글 |
| `GET` | `/api/books/marks` | 북마크 목록 조회 |

### Reviews

| Method | Endpoint | 설명 |
|--------|----------|------|
| `POST` | `/api/books/{bookId}/reviews` | 리뷰 작성 |
| `PUT` | `/api/books/{bookId}/reviews/{reviewId}` | 리뷰 수정 |
| `DELETE` | `/api/books/{bookId}/reviews/{reviewId}` | 리뷰 삭제 |
| `GET` | `/api/books/{bookId}/reviews` | 리뷰 목록 조회 |

### Internal (서비스 간 통신)

| Method | Endpoint | 설명 |
|--------|----------|------|
| `GET` | `/internal/books/recommendations/{userId}` | 사용자 추천 도서 ID 목록 |
| `GET` | `/internal/books/alarms/books/{bookId}` | 도서 북마크 사용자 ID 목록 |

---

## 프로젝트 구조

```
src/main/java/.../
├── clientfeign/              # 외부 서비스 통신 (UserClient)
│   └── user/
├── common/
│   ├── enums/                # Category, MessageStatus
│   └── exception/            # BookException
├── config/                   # Web, Schedule 설정
├── domain/
│   ├── book/
│   │   ├── controller/       # REST API (BookController, InternalController)
│   │   ├── model/
│   │   │   ├── dto/          # Request/Response DTO
│   │   │   └── entity/       # Book, BookMark, BookReview
│   │   ├── repository/       # JPA Repository
│   │   └── service/          # 비즈니스 로직 (reader/writer 분리)
│   └── outbox/               # Outbox 패턴 엔티티
└── external/
    ├── gemini/               # Gemini AI 연동 (카테고리 분류, 이미지 분석)
    ├── naverbooks/           # Naver Books API 클라이언트
    └── rabbitmq/             # 메시지 발행 (Outbox 기반 알림)
```