package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ScheduleCustomRepository {
    Page<Schedule> findByEmployeeId(Long employeeId, Pageable pageable);

    Page<Schedule> searchSchedules(LocalDate startDate,
                                   LocalDate endDate,
                                   ScheduleType type,
                                   ScheduleStatus status,
                                   String name,
                                   String position,
                                   String department,
                                   Pageable pageable);

    Page<Schedule> findDailySchedules(LocalDate date, Pageable pageable);
    Page<Schedule> findWeeklySchedules(LocalDate date, Pageable pageable);
    Page<Schedule> findMonthlySchedules(LocalDate date, Pageable pageable);

}
