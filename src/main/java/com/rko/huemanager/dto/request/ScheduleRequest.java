package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Schema(title = "일정 관련 요청")
public record ScheduleRequest(
        @Schema(description = "시작 날짜", example = "2024-03-01")
        @NotNull
        LocalDate startDate,

        @Schema(description = "종료 날짜", example = "2024-03-01")
        @NotNull
        LocalDate endDate,
        @Schema(description = "일정 유형", example = "LEAVE")
        @NotNull
        ScheduleType type
) {
        public static ScheduleRequest of(LocalDate startDate, LocalDate endDate, ScheduleType type){
                return new ScheduleRequest(startDate, endDate, type);
        }
}
