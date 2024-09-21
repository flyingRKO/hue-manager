package com.rko.huemanager.controller;

import com.rko.huemanager.aop.RequireAdminRole;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.dto.ScheduleDto;
import com.rko.huemanager.dto.request.ScheduleSearchRequest;
import com.rko.huemanager.dto.request.StatusUpdateRequest;
import com.rko.huemanager.dto.response.MeetingRoomDto;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/search")
    @RequireAdminRole
    public Response<Page<ScheduleDto>> searchSchedules(@RequestBody ScheduleSearchRequest request,
                                                       @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable){
        return Response.success(adminService.searchSchedules(request, pageable));
    }

    @PostMapping("/schedule/{scheduleId}/status")
    @RequireAdminRole
    public Response<Void> updateScheduleStatus(@PathVariable Long scheduleId, @RequestBody StatusUpdateRequest request){
        adminService.updateScheduleStatus(scheduleId, request.status());
        return Response.success();
    }

    @GetMapping("/meeting-room")
    @RequireAdminRole
    public Response<Page<MeetingRoomDto>> getMeetingRooms(@RequestParam(required = false)ScheduleStatus status, Pageable pageable){
        return Response.success(adminService.getMeetingRoomsByStatus(status, pageable));
    }

    @PostMapping("/meeting-room/{meetingRoomId}/status")
    @RequireAdminRole
    public Response<Void> updateMeetingRoomStatus(@PathVariable Long meetingRoomId, @RequestParam ScheduleStatus status){
        adminService.updateMeetingRoomStatus(meetingRoomId, status);
        return Response.success();
    }
}
