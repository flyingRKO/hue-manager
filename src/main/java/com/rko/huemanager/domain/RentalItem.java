package com.rko.huemanager.domain;

import com.rko.huemanager.domain.constant.ItemStatus;
import com.rko.huemanager.domain.constant.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@Entity
public class RentalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Setter
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private String name;

    private String identifier;

    @Setter
    private LocalDate rentalDate;

    private RentalItem(ItemType itemType, ItemStatus itemStatus, String name, String identifier, LocalDate rentalDate) {
        this.itemType = itemType;
        this.itemStatus = itemStatus;
        this.name = name;
        this.identifier = identifier;
        this.rentalDate = rentalDate;
    }

    public static RentalItem of(ItemType itemType, ItemStatus itemStatus, String name, String identifier, LocalDate rentalDate) {
        return new RentalItem(itemType, itemStatus, name, identifier, rentalDate);
    }
}
