package com.rko.huemanager.dto.request;

public record ItemRentalRequest(
        Long itemId,
        String reason
) {
}
