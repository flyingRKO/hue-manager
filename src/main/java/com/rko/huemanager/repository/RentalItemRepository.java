package com.rko.huemanager.repository;

import com.rko.huemanager.domain.RentalItem;
import com.rko.huemanager.domain.constant.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalItemRepository extends JpaRepository<RentalItem, Long> {
    Page<RentalItem> findAllByItemType(ItemType itemType, Pageable pageable);
}
