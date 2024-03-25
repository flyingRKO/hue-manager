package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.ScheduleDto;
import com.rko.huemanager.dto.request.ScheduleRequest;
import com.rko.huemanager.dto.request.ScheduleSearchRequest;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/save")
    public Response<Void> saveSchedule(@AuthenticationPrincipal Employee employee, @RequestBody @Valid ScheduleRequest request){
        scheduleService.saveSchedule(employee.getId(), request);
        return Response.success();
    }

    @GetMapping("/all")
    public Response<Page<ScheduleDto>> getAllApprovedSchedules(@PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(scheduleService.getAllApprovedSchedules(pageable));
    }

    @GetMapping("/my")
    public Response<Page<ScheduleDto>> getMySchedules(@AuthenticationPrincipal Employee employee,
                                                      @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(scheduleService.getEmployeeSchedules(employee.getId(), pageable));
    }

    @GetMapping("/day")
    public Response<Page<ScheduleDto>> getDaySchedules(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                       @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(scheduleService.getDaySchedules(date, pageable));
    }

    @GetMapping("/week")
    public Response<Page<ScheduleDto>> getWeekSchedules(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                        @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(scheduleService.getWeekSchedules(date, pageable));
    }

    @GetMapping("/month")
    public Response<Page<ScheduleDto>> getMonthSchedules(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                         @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(scheduleService.getMonthSchedules(date, pageable));
    }


    @PutMapping("/{scheduleId}/update")
    public Response<Void> updateSchedule(@AuthenticationPrincipal Employee employee, @PathVariable Long scheduleId, @RequestBody @Valid ScheduleRequest request){
        scheduleService.updateSchedule(employee.getId(), scheduleId, request);
        return Response.success();
    }

    @DeleteMapping("/{scheduleId}")
    public Response<Void> deleteSchedule(@AuthenticationPrincipal Employee employee, @PathVariable Long scheduleId){
        scheduleService.deleteSchedule(employee.getId(), scheduleId);
        return Response.success();
    }
}
