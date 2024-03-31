package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import com.rko.huemanager.dto.ScheduleDto;
import com.rko.huemanager.dto.request.ScheduleSearchRequest;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public Page<ScheduleDto> searchSchedules(ScheduleSearchRequest request, Pageable pageable){
        Page<Schedule> schedules = scheduleRepository.findSearchSchedules(
                request.startDate(),
                request.endDate(),
                request.type(),
                request.status(),
                request.name(),
                request.position(),
                request.department(),
                pageable);

        return schedules.map(ScheduleDto::from);
    }
    @Transactional
    public void updateScheduleStatus(Long scheduleId, ScheduleStatus newStatus){
        Schedule schedule = findScheduleById(scheduleId);

        if (!schedule.getStatus().equals(ScheduleStatus.PENDING)){
            throw new HueManagerException(ErrorCode.SCHEDULE_NOT_PENDING);
        }

        schedule.setStatus(newStatus);

        if (newStatus == ScheduleStatus.REJECTED && schedule.getType() == ScheduleType.LEAVE){
            Employee employee = schedule.getEmployee();
            int days = Period.between(schedule.getStartDate(), schedule.getEndDate()).getDays() + 1;
            employee.setVacationCount(employee.getVacationCount() + days);
        }

    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.SCHEDULE_NOT_FOUND, String.format("scheduleId is %s", scheduleId)));
    }
}
