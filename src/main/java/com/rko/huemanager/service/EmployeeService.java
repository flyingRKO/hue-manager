package com.rko.huemanager.service;

import com.rko.huemanager.aop.Loggable;
import com.rko.huemanager.config.jwt.JwtTokenUtils;
import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.EmailUpdateRequest;
import com.rko.huemanager.dto.request.EmployeeInfoRequest;
import com.rko.huemanager.dto.request.PasswordUpdateRequest;
import com.rko.huemanager.dto.request.SignUpRequest;
import com.rko.huemanager.dto.response.SignUpResponse;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${jwt.token.expired}")
    private Long expiredTimeMs;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        employeeRepository.findByEmail(request.email()).ifPresent(it -> {
            throw new HueManagerException(ErrorCode.DUPLICATED_EMAIL, String.format("email is %s", request.email()));
        });

        Employee savedEmployee = employeeRepository.save(Employee.of(
                request.email(),
                encoder.encode(request.password()),
                request.name(),
                request.phoneNumber(),
                request.role(),
                request.position(),
                request.department()
        ));
        return SignUpResponse.fromEntity(savedEmployee);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email).orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("email is %s", email)));
    }

    @Loggable
    public String login(String email, String password){
        UserDetails savedEmployee = loadUserByUsername(email);
        if (!encoder.matches(password, savedEmployee.getPassword())) {
            throw new HueManagerException(ErrorCode.INVALID_PASSWORD);
        }
        return JwtTokenUtils.generateAccessToken(email, secretKey, expiredTimeMs);
    }

    @Transactional
    public void updateEmployeeInfo(Long employeeId, EmployeeInfoRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", employeeId)));

        if (request.name() != null){employee.setName(request.name());}
        if (request.phoneNumber() != null){employee.setPhoneNumber(request.phoneNumber());}
        if (request.position() != null){employee.setPosition(request.position());}
        if (request.department() != null){employee.setDepartment(request.department());}

    }

    @Transactional
    public void updateEmail(Long employeeId, EmailUpdateRequest request){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", employeeId)));

        if (!request.email().equals(employee.getEmail())) {
            employeeRepository.findByEmail(request.email()).ifPresent(existingEmployee -> {
                if (!existingEmployee.getId().equals(employee.getId())) {
                    throw new HueManagerException(ErrorCode.DUPLICATED_EMAIL, String.format("email is %s", request.email()));
                }
            });
            employee.setEmail(request.email());

        }
    }

    @Transactional
    public void updatePassword(Long employeeId, PasswordUpdateRequest request){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", employeeId)));

        if (!encoder.matches(request.currentPassword(), employee.getPassword())){
            throw new HueManagerException(ErrorCode.INVALID_PASSWORD);
        }

        if(request.newPassword().equals(request.currentPassword())){
            throw new HueManagerException(ErrorCode.PASSWORD_SAME_OLD);
        }

        employee.setPassword(encoder.encode(request.newPassword()));
    }



}
