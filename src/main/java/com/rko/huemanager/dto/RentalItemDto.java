package com.rko.huemanager.dto;

import com.rko.huemanager.domain.RentalItem;
import com.rko.huemanager.domain.constant.ItemStatus;
import com.rko.huemanager.domain.constant.ItemType;

import java.time.LocalDate;

public record RentalItemDto(
        Long id,
        ItemType itemType,
        ItemStatus itemStatus,
        String name,
        String identifier,
        LocalDate rentalDate
) {
    public static RentalItemDto of(RentalItem rentalItem) {
        return new RentalItemDto(
                rentalItem.getId(),
                rentalItem.getItemType(),
                rentalItem.getItemStatus(),
                rentalItem.getName(),
                rentalItem.getIdentifier(),
                rentalItem.getRentalDate()
        );
    }
}
