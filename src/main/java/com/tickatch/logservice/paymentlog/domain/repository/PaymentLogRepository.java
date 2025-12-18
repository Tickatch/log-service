package com.tickatch.logservice.paymentlog.domain.repository;

import com.tickatch.logservice.paymentlog.domain.PaymentLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, UUID> {}
