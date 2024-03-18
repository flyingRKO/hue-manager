package com.rko.huemanager.domain;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@RequiredArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Employee employee;
    @Column(nullable = false)
    @Setter
    private LocalDate startDate;
    @Column(nullable = false)
    @Setter
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private ScheduleType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private ScheduleStatus status;

    private Schedule(Employee employee, LocalDate startDate, LocalDate endDate, ScheduleType type, ScheduleStatus status) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
    }


    public static Schedule of(Employee employee, LocalDate startDate, LocalDate endDate, ScheduleType type, ScheduleStatus status) {
        return new Schedule(employee, startDate, endDate, type, status);
    }

}
