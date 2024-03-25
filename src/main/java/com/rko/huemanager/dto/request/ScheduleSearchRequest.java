package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(title = "일정 검색 요청")
public record ScheduleSearchRequest(
        @Schema(description = "시작 날짜", example = "2024-03-01")
        LocalDate startDate,
        @Schema(description = "종료 날짜", example = "2024-03-01")
        LocalDate endDate,
        @Schema(description = "일정 유형", example = "LEAVE")
        ScheduleType type,
        @Schema(description = "일정 상태", example = "PENDING")
        ScheduleStatus status
) {
}
