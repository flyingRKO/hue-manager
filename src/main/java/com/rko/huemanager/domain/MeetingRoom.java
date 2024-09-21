package com.rko.huemanager.domain;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
@Entity
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDate usageDate;

    @Setter
    private LocalTime startTime;

    @Setter
    private LocalTime endTime;

    @ManyToOne
    private Employee employee;

    @Setter
    private String usagePurpose;

    @Setter
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    private MeetingRoom(LocalDate usageDate, LocalTime startTime, LocalTime endTime, Employee employee, String usagePurpose, ScheduleStatus status) {
        this.usageDate = usageDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
        this.usagePurpose = usagePurpose;
        this.status = status;
    }

    public static MeetingRoom of(LocalDate usageDate, LocalTime startTime, LocalTime endTime, Employee employee, String usagePurpose, ScheduleStatus status) {
        return new MeetingRoom(usageDate, startTime, endTime, employee, usagePurpose, status);
    }
}
