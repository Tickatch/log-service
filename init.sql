CREATE SCHEMA IF NOT EXISTS log_service;

CREATE TABLE log_service.p_event_log (
                                          id              UUID PRIMARY KEY ,
                                          event_category  VARCHAR(50)  NOT NULL,
                                          event_type      VARCHAR(100) NOT NULL,
                                          action_type     VARCHAR(30)  NOT NULL,
                                          event_detail    JSONB,
                                          user_id         UUID,
                                          resource_id     VARCHAR(100),
                                          ip_address      VARCHAR(50),
                                          trace_id        VARCHAR(64),
                                          service_name    VARCHAR(100) NOT NULL,
                                          created_at      TIMESTAMP NOT NULL DEFAULT NOW()
);