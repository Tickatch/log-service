package com.tickatch.logservice.userlog.domain.repository;

import com.tickatch.logservice.userlog.domain.UserLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, UUID> {}
