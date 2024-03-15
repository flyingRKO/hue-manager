package com.rko.huemanager.domain;

import com.rko.huemanager.domain.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    @Setter
    private Integer vacationCount = 15;

    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private String department;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "removed_at")
    private Timestamp removedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    private Employee(String email, String password, String name, String phoneNumber, Role role, String position, String department){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.position = position;
        this.department = department;
    }

    public static Employee of(String email, String password, String name, String phoneNumber, Role role, String position, String department){
        return new Employee(email, password, name, phoneNumber, role, position, department);
    }

}
