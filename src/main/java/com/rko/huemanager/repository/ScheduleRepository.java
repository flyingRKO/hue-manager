package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustomRepository {
    Page<Schedule> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
