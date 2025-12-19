CREATE SCHEMA IF NOT EXISTS log_service;

CREATE TABLE log_service.p_reservation_seat_log
(
    id                  UUID PRIMARY KEY,

    reservation_seat_id BIGINT       NOT NULL,
    seat_number         VARCHAR(255) NOT NULL,

    action_type         VARCHAR(50)  NOT NULL,

    actor_type          VARCHAR(20)  NOT NULL,
    actor_user_id       UUID         NULL,

    occurred_at         TIMESTAMP    NOT NULL
);

CREATE TABLE log_service.p_arthall_domain_log
(
    id            UUID PRIMARY KEY,

    domain_type   VARCHAR(20) NOT NULL, -- ARTHALL | STAGE
    domain_id     BIGINT      NOT NULL, -- arthall_id 또는 stage_id

    action_type   VARCHAR(50) NOT NULL, -- ACTIVATED | INACTIVATED | DELETED

    actor_type    VARCHAR(20) NOT NULL,
    actor_user_id UUID        NULL,

    occurred_at   TIMESTAMP   NOT NULL
);

CREATE TABLE log_service.p_product_log
(
    id            UUID        NOT NULL,

    product_id    BIGINT      NOT NULL,

    action_type   VARCHAR(50) NOT NULL, -- CREATED, UPDATED, SUBMITTED_FOR_APPROVAL, APPROVED, REJECTED, RESUBMITTED, SALE_SCHEDULED, SALE_STARTED, SALE_CLOSED, COMPLETED, CANCELLED 등

    actor_type    VARCHAR(20) NOT NULL,
    actor_user_id UUID        NULL,

    occurred_at   TIMESTAMP   NOT NULL
);

CREATE TABLE log_service.p_reservation_log
(
    id                 UUID        NOT NULL,

    reservation_id     UUID        NOT NULL,
    reservation_number VARCHAR(255),

    action_type        VARCHAR(50) NOT NULL,

    actor_type         VARCHAR(20) NOT NULL,
    actor_user_id      UUID        NULL,

    occurred_at        TIMESTAMP   NOT NULL
);

CREATE TABLE log_service.p_ticket_log
(
    id             UUID         NOT NULL,

    ticket_id      UUID         NOT NULL,
    receive_method VARCHAR(255) NULL,

    action_type    VARCHAR(50)  NOT NULL,

    actor_type     VARCHAR(20)  NOT NULL,
    actor_user_id  UUID         NULL,

    occurred_at    TIMESTAMP    NOT NULL
);

CREATE TABLE log_service.p_payment_log
(
    id            UUID         NOT NULL,
    payment_id    UUID         NOT NULL,
    method        VARCHAR(255) NULL,
    retry_count   INT          NOT NULL DEFAULT 0,
    action_type   VARCHAR(50)  NOT NULL,
    actor_type    VARCHAR(20)  NOT NULL,
    actor_user_id UUID         NULL,
    occurred_at   TIMESTAMP    NOT NULL
);

CREATE TABLE log_service.p_user_log
(
    id            UUID        NOT NULL,
    user_id       UUID        NOT NULL,
    action_type   VARCHAR(50) NOT NULL,
    actor_type    VARCHAR(20) NOT NULL,
    actor_user_id UUID        NULL,
    occurred_at   TIMESTAMP   NOT NULL
);

CREATE TABLE log_service.p_auth_log
(
    id            UUID        NOT NULL,
    action_type   VARCHAR(50) NOT NULL,
    actor_type    VARCHAR(20) NOT NULL,
    actor_user_id UUID        NOT NULL, -- auth_id
    occurred_at   TIMESTAMP   NOT NULL
);
