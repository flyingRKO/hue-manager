package com.rko.huemanager.controller;

import com.rko.huemanager.aop.RequireAdminRole;
import com.rko.huemanager.dto.request.StatusUpdateRequest;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/schedule/{scheduleId}/status")
    @RequireAdminRole
    public Response<Void> updateScheduleStatus(@PathVariable Long scheduleId, @RequestBody StatusUpdateRequest request){
        adminService.updateScheduleStatus(scheduleId, request.status());
        return Response.success();
    }
}
