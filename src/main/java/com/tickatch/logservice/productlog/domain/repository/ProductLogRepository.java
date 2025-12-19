package com.tickatch.logservice.productlog.domain.repository;

import com.tickatch.logservice.productlog.domain.ProductLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, UUID> {}
