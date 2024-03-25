package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.LoginLog;
import com.rko.huemanager.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginLogService {
    private final LoginLogRepository loginLogRepository;

    public void saveLoginLog(Employee employee, String agent, String clientIp, LocalDateTime loginTime){
        loginLogRepository.save(LoginLog.of(employee,agent,clientIp,loginTime));
    }
}
