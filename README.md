# Tickatch Log Service

Tickatch 플랫폼 전반의 비즈니스 행위 이력(Audit Log)을 중앙에서 수집·저장하는 마이크로서비스입니다.  
각 도메인 서비스에서 발생하는 생성/변경/삭제/상태 변경 행위를 이벤트로 수신하여  
운영·클레임 대응을 위한 신뢰 가능한 이력 데이터를 제공합니다.

## 프로젝트 소개

Log Service는 개별 서비스의 운영 로그와 분리된 **비즈니스 행위 전용 로그 저장소** 역할을 수행합니다.

장애 발생 시 "무슨 오류가 났는가"가 아닌  
**"어떤 비즈니스 행위가 실제로 발생했는가"** 를 확인하기 위한 목적의 서비스입니다.

로그 적재 실패가 비즈니스 트랜잭션에 영향을 주지 않도록  
**RabbitMQ 기반 비동기 수집 구조**로 설계되었습니다.

## 기술 스택

| 분류 | 기술 |
|------|------|
| Framework | Spring Boot 3.x |
| Language | Java 21 |
| Database | PostgreSQL |
| Messaging | RabbitMQ |
| Query | JPA / QueryDSL |
| Security | Spring Security |

## 아키텍처

### 시스템 구성
```
Business Services
 (User, Auth, Product, Reservation, Payment, Ticket, ArtHall 등)
        │
        │  Domain Action Event
        ▼
     RabbitMQ
        │
        ▼
    Log Service
        │
        ▼
 PostgreSQL (서비스별 로그 테이블)
```

- 비즈니스 서비스는 도메인 행위 발생 시 이벤트만 발행
- Log Service는 이벤트를 비동기로 수신하여 로그 저장
- 로그 저장 실패는 원 서비스 트랜잭션에 영향 없음

### 레이어 구조
```
src/main/java/com/tickatch/logservice
├── LogServiceApplication.java
│
├── arthalllog
│   ├── domain
│   │   ├── ArtHallLog.java
│   │   ├── event
│   │   │   └── ArtHallLogEvent.java
│   │   └── repository
│   │       └── ArtHallLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── ArtHallLogConsumer.java
│
├── authlog
│   ├── domain
│   │   ├── AuthLog.java
│   │   ├── event
│   │   │   └── AuthEvent.java
│   │   └── repository
│   │       └── AuthLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── AuthLogConsumer.java
│
├── paymentlog
│   ├── domain
│   │   ├── PaymentLog.java
│   │   ├── event
│   │   │   └── PaymentEvent.java
│   │   └── repository
│   │       └── PaymentLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── PaymentLogConsumer.java
│
├── productlog
│   ├── domain
│   │   ├── ProductLog.java
│   │   ├── event
│   │   │   └── ProductEvent.java
│   │   └── repository
│   │       └── ProductLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── ProductLogConsumer.java
│
├── reservationlog
│   ├── domain
│   │   ├── ReservationLog.java
│   │   ├── event
│   │   │   └── ReservationEvent.java
│   │   └── repository
│   │       └── ReservationLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── ReservationLogConsumer.java
│
├── reservationseatlog
│   ├── domain
│   │   ├── ReservationSeatLog.java
│   │   ├── event
│   │   │   └── ReservationSeatEvent.java
│   │   └── repository
│   │       └── ReservationSeatLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── ReservationSeatLogConsumer.java
│
├── ticketlog
│   ├── domain
│   │   ├── TicketLog.java
│   │   ├── event
│   │   │   └── TicketEvent.java
│   │   └── repository
│   │       └── TicketLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── TicketLogConsumer.java
│
├── userlog
│   ├── domain
│   │   ├── UserLog.java
│   │   ├── event
│   │   │   └── UserEvent.java
│   │   └── repository
│   │       └── UserLogRepository.java
│   └── infrastructure
│       └── messaging
│           └── UserLogConsumer.java
│
└── global
    ├── audit
    │   └── AuditConfig.java
    ├── config
    │   ├── querydsl
    │   │   └── QuerydslConfig.java
    │   └── rabbitmq
    │       └── RabbitMQConfig.java
    └── security
        └── LogServiceSecurityConfig.java
```

## 설계 핵심

| 특징 | 설명 |
|------|------|
| 도메인별 로그 분리 | 서비스 단위로 로그 Aggregate 및 테이블 분리 |
| 이벤트 기반 수집 | RabbitMQ 기반 비동기 로그 수신 |
| Audit 중심 | Read 로그 제외, 핵심 도메인 행위만 저장 |
| Append Only | 로그 수정·삭제 금지 |
| 결합도 최소화 | 비즈니스 서비스는 로그 저장 로직을 알 필요 없음 |

### 로그 저장 대상

- **생성(Create)**
- **수정(Update)**
- **삭제(Delete)**
- **상태 변경(Status Change)**
  - 확정 / 취소 / 완료 등 주요 전이 이벤트

> 단순 조회(Read) 및 디버깅 로그는 저장하지 않습니다.

## 활용 시나리오

- **사용자 클레임 대응**  
  → "해당 시점에 어떤 상태 변경이 있었는지" 확인

- **장애 분석 보조**  
  → 기술 오류 이후 실제 비즈니스 영향 파악

- **운영 이력 조회**  
  → 관리자 기준 서비스·행위 단위 Audit 조회

## 이벤트 연동

### 수신 이벤트 (Consumer)

| 이벤트 | 발행 서비스 | 설명 |
|--------|------------|------|
| ArtHallLogEvent | ArtHall Service | 아트홀/스테이지/좌석 행위 로그 |
| ProductEvent | Product Service | 상품 상태 변경 로그 |
| ReservationEvent | Reservation Service | 예매 행위 로그 |
| PaymentEvent | Payment Service | 결제 행위 로그 |
| TicketEvent | Ticket Service | 티켓 발행/취소 로그 |
| UserEvent | User Service | 사용자 행위 로그 |
| AuthEvent | Auth Service | 인증/권한 변경 로그 |

## 데이터 모델 (ERD 예시)
```
┌────────────────────────────────────────────┐
│ p_arthall_domain_log                       │
├────────────────────────────────────────────┤
│ id (PK) UUID                               │
│ action_type VARCHAR                        │
│ actor_id UUID                              │
│ actor_type VARCHAR                         │
│ resource_id VARCHAR                        │
│ occurred_at TIMESTAMP                      │
└────────────────────────────────────────────┘
```

> **※** 모든 로그 테이블은 공통 컬럼 규약을 따르며,  
> 도메인 특성에 따라 확장 컬럼을 가질 수 있습니다.

## 실행 방법

### 환경 변수
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tickatch
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  rabbitmq:
    host: localhost
    port: 9092
    username: ${RABBITMQ_USERNAME}
    password: ${RABBITMQ_PASSWORD}
```

### 실행
```bash
./gradlew bootRun
```

### 테스트
```bash
./gradlew test
```

---

© 2025 Tickatch Team
