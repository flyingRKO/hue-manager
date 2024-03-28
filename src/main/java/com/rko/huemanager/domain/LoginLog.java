package com.rko.huemanager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Entity
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee employee;
    private String agent;
    private String clientIp;
    private LocalDateTime loginTime;

    private LoginLog(Employee employee, String agent, String clientIp, LocalDateTime loginTime) {
        this.employee = employee;
        this.agent = agent;
        this.clientIp = clientIp;
        this.loginTime = loginTime;
    }

    public static LoginLog of(Employee employee, String agent, String clientIp, LocalDateTime loginTime){
        return new LoginLog(employee, agent, clientIp, loginTime);
    }
}
