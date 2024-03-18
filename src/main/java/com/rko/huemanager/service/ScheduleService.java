package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import com.rko.huemanager.dto.request.SaveScheduleRequest;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import com.rko.huemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void saveSchedule(Long employeeId, SaveScheduleRequest request){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", employeeId)));

        if (request.type() == ScheduleType.LEAVE){
            long daysBetween = ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;
            if (employee.getVacationCount() < daysBetween){
                throw new HueManagerException(ErrorCode.NOT_ENOUGH_DAYS);
            }
            scheduleRepository.save(Schedule.of(employee, request.startDate(), request.endDate(), request.type(), ScheduleStatus.PENDING));

        } else if (request.type() == ScheduleType.NIGHT_SHIFT){
            if (!request.startDate().isEqual(request.endDate())){
                throw new HueManagerException(ErrorCode.INVALID_NIGHT_SHIFT_REQUEST);
            }
            scheduleRepository.save(Schedule.of(employee, request.startDate(), request.startDate(), request.type(), ScheduleStatus.PENDING));
        }
    }
}
