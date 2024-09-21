package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.MeetingRoom;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>, MeetingRoomCustomRepository {
    Page<MeetingRoom> findAllByEmployee(Employee employee, Pageable pageable);
    Page<MeetingRoom> findAllByStatus(ScheduleStatus status, Pageable pageable);
}
