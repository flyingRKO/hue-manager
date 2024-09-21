package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.MeetingRoomRequest;
import com.rko.huemanager.dto.response.MeetingRoomDto;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting-room")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @PostMapping("/create")
    public Response<Void> createMeetingRoom(@AuthenticationPrincipal Employee employee, @RequestBody MeetingRoomRequest request) {
        meetingRoomService.createMeetingRoom(employee, request);
        return Response.success();
    }

    @PostMapping("/update/{meetingRoomId}")
    public Response<Void> updateMeetingRoom(@AuthenticationPrincipal Employee employee, @PathVariable Long meetingRoomId, @RequestBody MeetingRoomRequest request) {
        meetingRoomService.updateMeetingRoom(employee, meetingRoomId, request);
        return Response.success();
    }

    @DeleteMapping("/delete/{meetingRoomId}")
    public Response<Void> deleteMeetingRoom(@AuthenticationPrincipal Employee employee, @PathVariable Long meetingRoomId) {
        meetingRoomService.deleteMeetingRoom(employee, meetingRoomId);
        return Response.success();
    }

    @GetMapping("{meetingRoomId}")
    public Response<MeetingRoomDto> getMeetingRoom(@AuthenticationPrincipal Employee employee, @PathVariable Long meetingRoomId) {
        return Response.success(meetingRoomService.getMeetingRoom(employee, meetingRoomId));
    }

    @GetMapping("/all")
    public Response<Page<MeetingRoomDto>> getAllMeetingRooms(@AuthenticationPrincipal Employee employee, Pageable pageable) {
        return Response.success(meetingRoomService.getAllMeetingRooms(employee, pageable));
    }
}