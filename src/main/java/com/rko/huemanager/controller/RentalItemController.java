package com.rko.huemanager.controller;

import com.rko.huemanager.aop.RequireAdminRole;
import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.ItemRequestDto;
import com.rko.huemanager.dto.RentalItemDto;
import com.rko.huemanager.dto.request.ItemRegisterRequest;
import com.rko.huemanager.dto.request.ItemRentalRequest;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.RentalItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RentalItemController {
    private final RentalItemService rentalItemService;

    @PostMapping("/admin/item/register")
    @RequireAdminRole
    public Response<Void> registerItem(@RequestBody ItemRegisterRequest request) {
        rentalItemService.registerItem(request);
        return Response.success();
    }
    @DeleteMapping("/admin/item/delete/{itemId}")
    @RequireAdminRole
    public Response<Void> deleteItem(@PathVariable Long itemId) {
        rentalItemService.deleteItem(itemId);
        return Response.success();
    }

    @GetMapping("/item/{itemId}")
    public Response<RentalItemDto> getItem(@PathVariable Long itemId) {
        return Response.success(rentalItemService.getItem(itemId));
    }

    @GetMapping("/item/cars")
    public Response<Page<RentalItemDto>> getCars(Pageable pageable) {
        return Response.success(rentalItemService.getCars(pageable));
    }

    @GetMapping("/item/laptops")
    public Response<Page<RentalItemDto>> getLaptops(Pageable pageable) {
        return Response.success(rentalItemService.getLaptops(pageable));
    }

    @PostMapping("/item/rental")
    public Response<Void> itemRequest(@AuthenticationPrincipal Employee employee, @RequestBody ItemRentalRequest request) {
        rentalItemService.requestItem(employee, request);
        return Response.success();
    }

    @DeleteMapping("/item/rental/{requestId}")
    public Response<Void> deleteItemRequest(@AuthenticationPrincipal Employee employee, @PathVariable Long requestId) {
        rentalItemService.deleteRentalRequest(employee, requestId);
        return Response.success();
    }

    @PostMapping("/admin/item/rental/approve/{requestId}")
    public Response<Void> approveItemRequest(@PathVariable Long requestId) {
        rentalItemService.approveRentalRequest(requestId);
        return Response.success();
    }

    @PostMapping("/admin/item/rental/reject/{requestId}")
    public Response<Void> rejectItemRequest(@PathVariable Long requestId) {
        rentalItemService.rejectRentalRequest(requestId);
        return Response.success();
    }

    @GetMapping("/item/rental/{requestId}")
    public Response<ItemRequestDto> getItemRequest(@PathVariable Long requestId) {
        return Response.success(rentalItemService.getItemRequest(requestId));
    }

    @GetMapping("/item/rental/myRequests")
    public Response<Page<ItemRequestDto>> getMyRequests(@AuthenticationPrincipal Employee employee, Pageable pageable) {
        return Response.success(rentalItemService.getMyRentalRequests(employee, pageable));
    }

    @GetMapping("/admin/item/rental/all")
    @RequireAdminRole
    public Response<Page<ItemRequestDto>> getAllRequests(Pageable pageable) {
        return Response.success(rentalItemService.getAllRentalRequests(pageable));
    }

}
