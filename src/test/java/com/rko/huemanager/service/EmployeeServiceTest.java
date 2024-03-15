package com.rko.huemanager.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.constant.Role;
import com.rko.huemanager.dto.request.SignUpRequest;
import com.rko.huemanager.dto.response.SignUpResponse;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void signUp_Succeed(){
        // Given
        String email = "new@example.com";
        SignUpRequest request = new SignUpRequest(email, "password", "Name", "010-1234-5678", Role.ROLE_EMP, "Developer", "IT");

        given(employeeRepository.findByEmail(email)).willReturn(Optional.empty());
        given(encoder.encode("password")).willReturn("encodedPassword");
        given(employeeRepository.save(any(Employee.class))).willAnswer(i -> i.getArgument(0));

        // When
        SignUpResponse result = employeeService.signUp(request);

        // Then
        assertThat(result.email()).isEqualTo(email);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void signUp_EmailExists(){
        // Given
        String email = "existing@example.com";
        SignUpRequest request = new SignUpRequest(email, "password", "Name", "010-1234-5678", Role.ROLE_EMP, "Developer", "IT");

        given(employeeRepository.findByEmail(email)).willReturn(Optional.of(new Employee()));

        // When & Then
        assertThrows(HueManagerException.class, () -> employeeService.signUp(request));

    }
}