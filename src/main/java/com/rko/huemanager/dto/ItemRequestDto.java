package com.rko.huemanager.dto;

import com.rko.huemanager.domain.ItemRequest;
import com.rko.huemanager.domain.constant.ScheduleStatus;

import java.time.LocalDate;

public record ItemRequestDto(
        Long id,
        Long rentalItemId,
        String rentalItemName,
        String reason,
        Long employeeId,
        String employeeName,
        ScheduleStatus status,
        LocalDate requestDate,
        LocalDate confirmDate
) {
    public static ItemRequestDto from(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getRentalItem().getId(),
                itemRequest.getRentalItem().getName(),
                itemRequest.getReason(),
                itemRequest.getEmployee().getId(),
                itemRequest.getEmployee().getName(),
                itemRequest.getStatus(),
                itemRequest.getRequestDate(),
                itemRequest.getConfirmDate()
        );
    }
}
