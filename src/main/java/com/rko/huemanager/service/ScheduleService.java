package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import com.rko.huemanager.dto.ScheduleDto;
import com.rko.huemanager.dto.request.ScheduleRequest;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import com.rko.huemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void saveSchedule(Long employeeId, ScheduleRequest request) {
        Employee employee = findEmployeeById(employeeId);
        validateScheduleRequest(request, employee);
        LocalDate endDate = (request.type() == ScheduleType.NIGHT_SHIFT) ? request.startDate() : request.endDate();
        Schedule schedule = Schedule.of(employee, request.startDate(), endDate, request.type(), ScheduleStatus.PENDING);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public Page<ScheduleDto> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(ScheduleDto::from);
    }

    @Transactional
    public Page<ScheduleDto> getEmployeeSchedules(Long employeeId, Pageable pageable){
        Page<Schedule> schedules = scheduleRepository.findByEmployeeId(employeeId, pageable);
        return schedules.map(ScheduleDto::from);
    }

    @Transactional
    public void updateSchedule(Long employeeId, Long scheduleId, ScheduleRequest request) {
        Employee employee = findEmployeeById(employeeId);
        Schedule schedule = findScheduleById(scheduleId);

        validateScheduleForUpdate(schedule, request, employee);

        schedule.setStartDate(request.startDate());
        schedule.setEndDate((request.type() == ScheduleType.NIGHT_SHIFT) ? request.startDate() : request.endDate());
        schedule.setType(request.type());
    }

    @Transactional
    public void deleteSchedule(Long employeeId, Long scheduleId){
        Employee employee = findEmployeeById(employeeId);
        Schedule schedule = findScheduleById(scheduleId);

        validateScheduleAuthorized(schedule, employee);
        scheduleRepository.delete(schedule);
    }

    private Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", employeeId)));
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.SCHEDULE_NOT_FOUND, String.format("scheduleId is %s", scheduleId)));
    }

    private void validateScheduleRequest(ScheduleRequest request, Employee employee) {
        if (request.type() == ScheduleType.LEAVE && !hasEnoughDaysForLeave(request, employee)) {
            throw new HueManagerException(ErrorCode.NOT_ENOUGH_DAYS);
        }
        if (request.type() == ScheduleType.NIGHT_SHIFT && !request.startDate().isEqual(request.endDate())) {
            throw new HueManagerException(ErrorCode.INVALID_NIGHT_SHIFT_REQUEST);
        }
    }

    private boolean hasEnoughDaysForLeave(ScheduleRequest request, Employee employee) {
        long daysBetween = ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;
        return employee.getVacationCount() >= daysBetween;
    }


    private void validateScheduleForUpdate(Schedule schedule, ScheduleRequest request, Employee employee) {
        validateScheduleAuthorized(schedule, employee);
        validateScheduleRequest(ScheduleRequest.of(request.startDate(), request.endDate(), request.type()), employee);
    }

    private void validateScheduleAuthorized(Schedule schedule, Employee employee){
        if (!schedule.getStatus().equals(ScheduleStatus.PENDING)) {
            throw new HueManagerException(ErrorCode.SCHEDULE_NOT_PENDING);
        }
        if (!schedule.getEmployee().getId().equals(employee.getId())){
            throw new HueManagerException(ErrorCode.UNAUTHORIZED_SCHEDULE);
        }
    }
}
