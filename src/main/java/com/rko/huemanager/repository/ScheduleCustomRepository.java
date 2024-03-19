package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleCustomRepository {
    Page<Schedule> findByEmployeeId(Long employeeId, Pageable pageable);
}
