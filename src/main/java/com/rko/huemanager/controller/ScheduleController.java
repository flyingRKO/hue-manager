package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.SaveScheduleRequest;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/save")
    public Response<Void> saveSchedule(@AuthenticationPrincipal Employee employee, @RequestBody @Valid SaveScheduleRequest request){
        scheduleService.saveSchedule(employee.getId(), request);
        return Response.success();
    }
}
