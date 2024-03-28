package com.rko.huemanager.aop;

import com.rko.huemanager.config.jwt.JwtTokenUtils;
import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import com.rko.huemanager.service.LoginLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;


@Aspect
@Component
@RequiredArgsConstructor
public class LoginLoggingAspect {

    private final LoginLogService loginLogService;
    private final EmployeeRepository employeeRepository;
    @Value("${jwt.token.secret}")
    private String secretKey;

    @AfterReturning(value = "@annotation(Loggable)", returning = "token")
    public void logLogin(String token){
        if (token != null && !JwtTokenUtils.isTokenExpired(token, secretKey)){
            String email = JwtTokenUtils.getUsername(token, secretKey);
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String agent = request.getHeader("User-Agent");
            String clientIp = request.getRemoteAddr();

            Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("email is %s", email)));
            if (employee != null){
                loginLogService.saveLoginLog(employee, agent, clientIp, LocalDateTime.now());
            }
        }

    }
}
