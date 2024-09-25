package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ItemType;

public record ItemRegisterRequest(
        ItemType itemType,
        String itemName,
        String identifier
) {
}
