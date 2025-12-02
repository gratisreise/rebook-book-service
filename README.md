# Rebook Book Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-FF6600)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 도서 관리 서비스**

도서 검색, 등록, 추천, 리뷰, 북마크 기능을 제공하는 핵심 비즈니스 서비스

</div>

---

## 1. 개요

**Rebook Book Service**는 중고 도서 거래 플랫폼 Rebook의 핵심 백엔드 마이크로서비스로, 도서 정보를 관리하고 사용자에게 개인화된 경험을 제공합니다. Spring Boot 기반으로 구현된 본 서비스는 **서비스 디스커버리**, **중앙화된 설정 관리**, **비동기 메시징**을 통한 확장 가능한 구조를 제공합니다.


### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **도서 관리**: 도서 정보 등록, 조회, 검색 및 메타데이터 관리
- **외부 API 통합**: Naver Books API를 통한 외부 도서 검색
- **AI 기반 분류**: Gemini AI를 활용한 자동 카테고리 분류
- **리뷰 시스템**: 사용자 리뷰 및 평점 관리
- **북마크 기능**: 사용자별 관심 도서 북마킹
- **추천 시스템**: User Service 연동을 통한 개인화 도서 추천
- **알림 발행**: RabbitMQ를 통한 신규 도서 등록 알림 전송

---

## 2. 목차

- [주요 기능](#3-주요-기능)
- [기술 스택](#4-기술-스택)
- [아키텍처](#5-아키텍처)
- [API 문서](#6-api-문서)
- [프로젝트 구조](#7-프로젝트-구조)

---

## 3. 주요 기능

### 3.1 도서 관리

#### 도서 검색 및 등록
- ✅ Naver Books API를 통한 외부 도서 검색
- ✅ 신규 도서 등록 (ISBN, 제목, 저자, 출판사 등)
- ✅ Gemini AI 기반 자동 카테고리 분류
- ✅ 도서 메타데이터 관리 (이미지, 설명 등)

#### 도서 조회
- ✅ 도서 상세 정보 조회 (북마크 상태 포함)
- ✅ 도서 목록 조회 (페이지네이션 지원)
- ✅ 키워드 기반 도서 검색
- ✅ 사용자 맞춤 도서 추천 (User Service 연동)

### 3.2 리뷰 시스템

- ✅ 도서 리뷰 작성 및 평점 등록
- ✅ 도서 리뷰 수정 및 삭제 (작성자 권한 검증)
- ✅ 특정 도서의 리뷰 목록 조회 (페이지네이션 지원)
- ✅ User Service 연동을 통한 리뷰 작성자 정보 표시

### 3.3 북마크 시스템

- ✅ 도서 북마크 추가/삭제 토글 기능 (중복 방지)
- ✅ 내 북마크 목록 조회 (페이지네이션 지원)
- ✅ 도서 조회 시 현재 사용자의 북마크 상태 표시
- ✅ 복합키(Composite Key) 기반 효율적인 북마크 관리 (userId + bookId)

### 3.4 추천 시스템

- ✅ 사용자 맞춤 도서 추천
- ✅ User Service와 OpenFeign 연동을 통한 개인화 추천
- ✅ 사용자 선호도 기반 도서 필터링

### 3.5 알림 시스템 (Event-Driven)

- ✅ 신규 도서 등록 시 관심 카테고리 사용자에게 알림 발행
- ✅ RabbitMQ 기반 비동기 메시징 (Notification Service 연동)
- ✅ JSON 메시지 직렬화를 통한 안정적 메시지 전송


---

## 4. 기술 스택

### 4.1 백엔드 프레임워크

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Boot** | 3.3.13 | 애플리케이션 프레임워크 |
| **Java** | 17 | 프로그래밍 언어 |
| **Spring Data JPA** | - | ORM 및 데이터 접근 계층 |
| **Spring Validation** | - | 요청 데이터 유효성 검증 |
| **Lombok** | - | 보일러플레이트 코드 제거 |

### 4.2 데이터베이스 & 캐싱

| 기술 | 버전 | 용도 |
|------|------|------|
| **PostgreSQL** | 13+ | 관계형 데이터베이스 (메인 데이터 저장소) |
| **Redis** | 6+ | 분산 캐싱 및 세션 관리 |

### 4.3 마이크로서비스 인프라 (Spring Cloud)

| 기술 | 버전 | 용도 |
|------|------|------|
| **Eureka Client** | 2023.0.5 | 서비스 디스커버리 및 등록 |
| **Spring Cloud Config** | 2023.0.5 | 중앙화된 설정 관리 (외부 Config Server) |
| **OpenFeign** | 2023.0.5 | 선언적 HTTP 클라이언트 (User Service, Naver API 연동) |

### 4.4 메시징 & 이벤트

| 기술 | 버전 | 용도 |
|------|------|------|
| **RabbitMQ (AMQP)** | 3.x | 비동기 메시징 및 이벤트 발행 |
| **Spring AMQP** | - | RabbitMQ 통합 및 메시지 컨버터 |

### 4.5 외부 API 통합

| 기술 | 버전 | 용도 |
|------|------|------|
| **Naver Books API** | - | 외부 도서 검색 (OpenFeign 기반) |
| **Gemini AI** | 1.0.0 | 자동 카테고리 분류 (7개 카테고리) |
| **AWS SDK S3** | 2.27.21 | 도서 이미지 저장 (의존성만 포함) |

### 4.6 모니터링 & 로깅

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Actuator** | - | 헬스체크 및 메트릭 엔드포인트 |
| **Prometheus** | - | 메트릭 수집 및 모니터링 |
| **Sentry** | 8.13.2 | 실시간 에러 트래킹 및 알림 |
| **SLF4J & Logback** | - | 애플리케이션 로깅 |

### 4.7 API 문서화

| 기술 | 버전 | 용도 |
|------|------|------|
| **SpringDoc OpenAPI 3** | 2.6.0 | Swagger UI 기반 REST API 문서 자동 생성 |

### 4.8 빌드 & 배포

| 기술 | 버전 | 용도 |
|------|------|------|
| **Gradle** | 8.14.2 | 빌드 자동화 도구 |
| **Docker** | - | 컨테이너화 및 배포 |
| **Jacoco** | - | 테스트 커버리지 분석 |
| **JUnit 5** | - | 단위 테스트 프레임워크 |

---

## 5. 아키텍처

### 5.1 마이크로서비스 아키텍처

```
┌─────────────────┐
│  API Gateway    │ ← 인증 및 라우팅 (X-User-Id 헤더 주입)
└────────┬────────┘
         │
         ├──────────────┬──────────────┬──────────────┐
         │              │              │              │
    ┌────▼────┐    ┌────▼────┐   ┌────▼────┐    ┌────▼────┐
    │  Book   │    │ Trading │   │  User   │    │ Notif.  │
    │ Service │───►│ Service │   │ Service │    │ Service │
    └────┬────┘    └─────────┘   └────▲────┘    └────▲────┘
         │         (OpenFeign)         │              │
         │                             │              │
         │         ┌──────────────┐    │              │
         └────────►│  RabbitMQ    │────┴──────────────┘
                   │  (AMQP)      │  (Notification Events)
                   └──────────────┘

    ┌─────────────────────────────────────────────┐
    │        Infrastructure Services              │
    ├─────────────────────────────────────────────┤
    │  Eureka  │  Config Server  │  PostgreSQL   │
    │  Redis   │  Naver API      │  Gemini AI   │
    └─────────────────────────────────────────────┘
```


### 5.2 엔티티 관계도 (ERD)

```
┌─────────────────────────────┐
│        Book                 │
├─────────────────────────────┤
│ PK  id (Long)               │
│     isbn (String)           │
│     title (String)          │
│     author (String)         │
│     publisher (String)      │
│     category (String)       │
│     imageUrl (String)       │
│     description (String)    │
│     createdAt (LocalDateTime)│
│     updatedAt (LocalDateTime)│
└─────────────────────────────┘
              │
              ├──────────────┐
              │              │
              │ 1:N          │ 1:N
              │              │
              ▼              ▼
┌─────────────────────────────┐    ┌─────────────────────────────┐
│      BookReview             │    │      BookMark               │
├─────────────────────────────┤    ├─────────────────────────────┤
│ PK  id (Long)               │    │ PK  userId (String)         │ ← Composite Key
│     bookId (Long)           │    │ PK  bookId (Long)           │ ← Composite Key
│     userId (String)         │    │     createdAt (LocalDateTime)│
│     rating (Integer)        │    └─────────────────────────────┘
│     content (String)        │
│     createdAt (LocalDateTime)│
│     updatedAt (LocalDateTime)│
└─────────────────────────────┘
```
**엔티티 설계 패턴**:
- **JPA Auditing**: `@EntityListeners(AuditingEntityListener.class)`로 생성/수정 시간 자동 관리
- **복합키 (Composite Key)**: `BookMarkId` 임베디드 클래스로 사용자-도서 다대다 관계 표현
- **인덱싱**: ISBN, 제목, 저자, 카테고리에 대한 검색 최적화


### 5.3 도서저장흐름
![도서저장](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/book_save.png)


## 6. API 문서

### 6.1 Swagger UI 접근


### 6.1 API 엔드포인트 상세

#### 6.1.1 도서 관리 API (`BookController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **GET** | `/api/books/external/search` | 네이버 API 도서 검색 |
| **POST** | `/api/books` | 신규 도서 등록 |
| **GET** | `/api/books` | 도서 목록 조회 (페이징) |
| **GET** | `/api/books/search` | 키워드 기반 도서 검색 |
| **GET** | `/api/books/{bookId}` | 도서 상세 정보 조회 |
| **GET** | `/api/books/recommendations` | 맞춤 추천 도서 조회 |

#### 6.1.2 리뷰 관리 API (`BookReviewController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/books/{bookId}/reviews` | 도서 리뷰 작성 |
| **PUT** | `/api/books/{bookId}/reviews/{reviewId}` | 도서 리뷰 수정 |
| **DELETE** | `/api/books/{bookId}/reviews/{reviewId}` | 도서 리뷰 삭제 |
| **GET** | `/api/books/{bookId}/reviews` | 특정 도서 리뷰 조회 (페이징) |

#### 6.1.3 북마크 관리 API (`BookMarkController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/books/{bookId}/marks` | 북마크 추가/삭제 (토글) |
| **GET** | `/api/books/marks` | 내 북마크 목록 조회 (페이징) |

#### 6..4 내부 서비스 통신 API

| Method | Endpoint | Summary | 용도 |
|--------|----------|---------|------|
| **GET** | `/api/books/recommendations/{userId}` | 추천 도서 ID 목록 조회 | Trading Service 연동 |
| **GET** | `/alarms/books/{bookId}` | 도서 찜한 사용자 ID 목록 조회 | Notification Service 연동 |


## 7. 프로젝트 구조

### 주요 디렉토리 설명

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **advice/** | 전역 예외 처리 | `@RestControllerAdvice`로 모든 컨트롤러 예외 통합 핸들링 |
| **common/** | 응답 표준화 | 통일된 API 응답 구조 제공 (`CommonResult`, `SingleResult`, `ListResult`) |
| **config/** | 인프라 설정 | RabbitMQ, Swagger 등 외부 서비스 연동 설정 |
| **controller/** | REST API | 엔드포인트 정의 및 Swagger 문서화 (`@Tag`, `@Operation`) |
| **model/entity/** | 엔티티 모델 | JPA 엔티티 및 복합키 정의 |
| **model/** (DTO) | 데이터 전송 | 요청/응답 DTO, Naver/User 연동 모델, 메시징 DTO |
| **utils/** | 유틸리티 | 메시지 발행 등 공통 유틸리티 |
| **exception/** | 커스텀 예외 | 도메인별 예외 클래스 (404, 409, 403, 400) |
| **feigns/** | 서비스 간 통신 | OpenFeign을 통한 User Service, Naver API 동기 호출 |
| **repository/** | 데이터 접근 | Spring Data JPA 리포지토리 인터페이스 |
| **service/** | 비즈니스 로직 | 트랜잭션 관리, 권한 검증, 외부 서비스 연동 |

### 구조
```
rebook-book-service/
├── src/main/java/com/example/rebookbookservice/
│   ├── advice/                 # 전역 예외 처리
│   ├── common/                 # 공통 응답 모델
│   ├── config/                 # RabbitMQ, Swagger 설정
│   ├── controller/             # REST API 엔드포인트
│   ├── exception/              # 커스텀 예외 클래스
│   ├── feigns/                 # Naver API, User Service 연동
│   ├── model/
│   │   ├── entity/            # JPA 엔티티 (Book, BookReview, BookMark)
│   │   ├── naver/             # Naver API DTO
│   │   ├── user/              # User Service DTO
│   │   └── message/           # RabbitMQ 메시징 DTO
│   ├── repository/             # Spring Data JPA 리포지토리
│   ├── service/                # 비즈니스 로직 및 Reader 계층
│   └── utils/                  # NotificationPublisher 등 유틸리티
│
├── src/main/resources/
│   ├── application.yaml        # Spring Cloud Config 연동
│   ├── application-dev.yaml    # 개발 환경 설정
│   └── application-prod.yaml   # 운영 환경 설정
│
├── build.gradle                # Gradle 빌드 스크립트
├── Dockerfile                  # Docker 이미지 빌드
└── README.md                   # 프로젝트 문서
```