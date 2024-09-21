package com.rko.huemanager.repository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MeetingRoomCustomRepository {
    boolean existsByDateAndTimeRange(LocalDate usageDate, LocalTime startTime, LocalTime endTime);
}
