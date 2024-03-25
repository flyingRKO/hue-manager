package com.rko.huemanager.dto;

import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;

import java.time.LocalDate;

public record ScheduleDto(
        Long scheduleId,
        EmployeeDto employeeDto,
        LocalDate startDate,
        LocalDate endDate,
        ScheduleType type,
        ScheduleStatus status
) {
    public static ScheduleDto of(Long scheduleId, EmployeeDto employeeDto, LocalDate startDate, LocalDate endDate, ScheduleType type, ScheduleStatus status){
        return new ScheduleDto(scheduleId, employeeDto, startDate, endDate, type, status);
    }

    public static ScheduleDto from(Schedule schedule){
        return ScheduleDto.of(
                schedule.getId(),
                EmployeeDto.of(
                        schedule.getEmployee().getEmail(),
                        schedule.getEmployee().getName(),
                        schedule.getEmployee().getPosition(),
                        schedule.getEmployee().getDepartment()
                ),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getType(),
                schedule.getStatus()
        );
    }
}
