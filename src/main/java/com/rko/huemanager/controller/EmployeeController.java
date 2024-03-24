package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.*;
import com.rko.huemanager.dto.response.LoginResponse;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.dto.response.SignUpResponse;
import com.rko.huemanager.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/signup")
    public Response<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return Response.success(employeeService.signUp(request));
    }

    @PostMapping("/login")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = employeeService.login(request.email(), request.password());
        return Response.success(new LoginResponse(token));
    }

    @PutMapping("/info")
    public Response<Void> updateEmployeeInfo(@AuthenticationPrincipal Employee employee, @Valid @RequestBody EmployeeInfoRequest request){
        employeeService.updateEmployeeInfo(employee.getId(), request);
        return Response.success();
    }

    @PutMapping("/email")
    public Response<Void> updateEmail(@AuthenticationPrincipal Employee employee, @Valid @RequestBody EmailUpdateRequest request){
        employeeService.updateEmail(employee.getId(), request);
        return Response.success();
    }

    @PutMapping("/password")
    public Response<Void> updatePassword(@AuthenticationPrincipal Employee employee, @Valid @RequestBody PasswordUpdateRequest request){
        employeeService.updatePassword(employee.getId(), request);
        return Response.success();
    }

}
