package com.rko.huemanager.dto.response;

import com.rko.huemanager.domain.MeetingRoom;
import com.rko.huemanager.domain.constant.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record MeetingRoomDto(
        Long id,
        LocalDate usageDate,
        LocalTime startTime,
        LocalTime endTime,
        String usagePurpose,
        String employeeName,
        String employeePosition,
        String employeeDepartment,
        ScheduleStatus status)
{
    public static MeetingRoomDto from(MeetingRoom meetingRoom) {
        return new MeetingRoomDto(
                meetingRoom.getId(),
                meetingRoom.getUsageDate(),
                meetingRoom.getStartTime(),
                meetingRoom.getEndTime(),
                meetingRoom.getUsagePurpose(),
                meetingRoom.getEmployee().getName(),
                meetingRoom.getEmployee().getPosition(),
                meetingRoom.getEmployee().getDepartment(),
                meetingRoom.getStatus()
        );
    }
}
