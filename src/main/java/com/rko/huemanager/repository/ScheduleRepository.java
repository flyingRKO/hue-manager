package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustomRepository {
    Page<Schedule> findByStartDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, ScheduleStatus status, Pageable pageable);
    Page<Schedule> findByStatus(ScheduleStatus status, Pageable pageable);
}
