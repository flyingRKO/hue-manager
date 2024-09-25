package com.rko.huemanager.domain;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@Entity
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RentalItem rentalItem;

    private String reason;

    @ManyToOne
    private Employee employee;

    @Setter
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "updated_date")
    private LocalDate confirmDate;

    @PrePersist
    void requestDate() {
        this.requestDate = LocalDate.now();
    }

    @PreUpdate
    void confirmDate() {
        this.confirmDate = LocalDate.now();
    }

    private ItemRequest(RentalItem rentalItem, String reason, Employee employee, ScheduleStatus status) {
        this.rentalItem = rentalItem;
        this.reason = reason;
        this.employee = employee;
        this.status = status;
    }

    public static ItemRequest of(RentalItem rentalItem, String reason, Employee employee, ScheduleStatus status) {
        return new ItemRequest(rentalItem, reason, employee, status);
    }
}
