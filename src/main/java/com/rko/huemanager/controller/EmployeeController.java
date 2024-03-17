package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.EmployeeInfoRequest;
import com.rko.huemanager.dto.request.LoginRequest;
import com.rko.huemanager.dto.request.SignUpRequest;
import com.rko.huemanager.dto.response.LoginResponse;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.dto.response.SignUpResponse;
import com.rko.huemanager.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("info/{employeeId}")
    public Response<Void> updateEmployeeInfo(@PathVariable Long employeeId, @Valid @RequestBody EmployeeInfoRequest request){
        employeeService.updateEmployeeInfo(employeeId, request);
        return Response.success();
    }
}
