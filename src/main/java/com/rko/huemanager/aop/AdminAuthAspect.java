package com.rko.huemanager.aop;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.constant.Role;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAuthAspect {

    @Around("@annotation(com.rko.huemanager.aop.RequireAdminRole)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof Employee)) {
            throw new HueManagerException(ErrorCode.NOT_AN_EMPLOYEE);
        }
        Employee employee = (Employee) authentication.getPrincipal();

        if (!employee.getRole().equals(Role.ROLE_ADMIN)){
            throw new HueManagerException(ErrorCode.NOT_AN_ADMIN);
        }
        return joinPoint.proceed();
    }
}
