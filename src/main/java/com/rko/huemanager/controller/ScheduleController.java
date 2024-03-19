package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.ScheduleRequest;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{scheduleId}/update")
    public Response<Void> updateSchedule(@AuthenticationPrincipal Employee employee, @PathVariable Long scheduleId, @RequestBody @Valid ScheduleRequest request){
        scheduleService.updateSchedule(employee.getId(), scheduleId, request);
        return Response.success();
    }
}
