package com.rko.huemanager.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record MeetingRoomRequest(
        LocalDate usageDate,
        LocalTime startTime,
        LocalTime endTime,
        String usagePurpose
    )
{
}
