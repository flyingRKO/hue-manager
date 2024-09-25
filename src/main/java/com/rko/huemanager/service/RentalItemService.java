package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.ItemRequest;
import com.rko.huemanager.domain.RentalItem;
import com.rko.huemanager.domain.constant.ItemStatus;
import com.rko.huemanager.domain.constant.ItemType;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.dto.ItemRequestDto;
import com.rko.huemanager.dto.RentalItemDto;
import com.rko.huemanager.dto.request.ItemRegisterRequest;
import com.rko.huemanager.dto.request.ItemRentalRequest;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.ItemRequestRepository;
import com.rko.huemanager.repository.RentalItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentalItemService {
    private final RentalItemRepository rentalItemRepository;

    private final ItemRequestRepository itemRequestRepository;

    @Transactional
    public void registerItem(ItemRegisterRequest request){
        RentalItem rentalItem = RentalItem.of(
                request.itemType(),
                ItemStatus.AVAILABLE,
                request.itemName(),
                request.identifier(),
                null
        );

        rentalItemRepository.save(rentalItem);
    }

    @Transactional
    public void deleteItem(Long itemId){
        RentalItem rentalItem = rentalItemRepository.findById(itemId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM));
        rentalItemRepository.deleteById(itemId);
    }

    @Transactional(readOnly = true)
    public RentalItemDto getItem(Long itemId){
        RentalItem rentalItem = rentalItemRepository.findById(itemId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM));
        return RentalItemDto.of(rentalItem);
    }

    @Transactional(readOnly = true)
    public Page<RentalItemDto> getCars(Pageable pageable){
        Page<RentalItem> rentalItems = rentalItemRepository.findAllByItemType(ItemType.CAR, pageable);
        return rentalItems.map(RentalItemDto::of);
    }

    @Transactional(readOnly = true)
    public Page<RentalItemDto> getLaptops(Pageable pageable){
        Page<RentalItem> rentalItems = rentalItemRepository.findAllByItemType(ItemType.LAPTOP, pageable);
        return rentalItems.map(RentalItemDto::of);
    }

    @Transactional
    public void requestItem(Employee employee, ItemRentalRequest request){
        RentalItem rentalItem = rentalItemRepository.findById(request.itemId())
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM));

        if (rentalItem.getItemStatus() == ItemStatus.RENTED) {
            throw new HueManagerException(ErrorCode.ALREADY_RENTED);
        }

        ItemRequest itemRequest = ItemRequest.of(
                rentalItem,
                request.reason(),
                employee,
                ScheduleStatus.PENDING
        );

        itemRequestRepository.save(itemRequest);
    }

    @Transactional
    public void deleteRentalRequest(Employee employee, Long requestId){
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM_REQUEST));
        if (!itemRequest.getEmployee().getId().equals(employee.getId())) {
            throw new HueManagerException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        itemRequestRepository.deleteById(requestId);
    }

    @Transactional
    public void approveRentalRequest(Long requestId){
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM_REQUEST));

        RentalItem rentalItem = itemRequest.getRentalItem();

        if (itemRequest.getStatus() != ScheduleStatus.PENDING) {
            throw new HueManagerException(ErrorCode.INVALID_STATUS);
        }

        itemRequest.setStatus(ScheduleStatus.APPROVED);
        rentalItem.setItemStatus(ItemStatus.RENTED);
        rentalItem.setRentalDate(LocalDate.now());
    }

    @Transactional
    public void rejectRentalRequest(Long requestId){
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM_REQUEST));

        if (itemRequest.getStatus() != ScheduleStatus.PENDING) {
            throw new HueManagerException(ErrorCode.INVALID_STATUS);
        }

        itemRequest.setStatus(ScheduleStatus.REJECTED);
    }

    @Transactional(readOnly = true)
    public ItemRequestDto getItemRequest(Long requestId){
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.NOT_FOUND_ITEM_REQUEST));
        return ItemRequestDto.from(itemRequest);
    }

    @Transactional(readOnly = true)
    public Page<ItemRequestDto> getMyRentalRequests(Employee employee, Pageable pageable){
        Page<ItemRequest> itemRequests = itemRequestRepository.findAllByEmployee(employee, pageable);
        return itemRequests.map(ItemRequestDto::from);
    }
    @Transactional(readOnly = true)
    public Page<ItemRequestDto> getAllRentalRequests(Pageable pageable){
        Page<ItemRequest> itemRequests = itemRequestRepository.findAll(pageable);
        return itemRequests.map(ItemRequestDto::from);
    }
}
