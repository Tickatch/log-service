package com.tickatch.logservice.reservationseatlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationSeatEvent(
    UUID eventId, // 발행 서비스에서 생성하는 이벤트 ID
    Long reservationSeatId,
    String seatNumber,
    String actionType, // 좌석선점 / 예약확정 / 예약취소 / 좌석삭제
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
