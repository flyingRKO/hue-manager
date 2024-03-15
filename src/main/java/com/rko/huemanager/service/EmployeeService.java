package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.SignUpRequest;
import com.rko.huemanager.dto.response.SignUpResponse;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder encoder;

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
}
