package com.rko.huemanager.controller;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.*;
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

    @PutMapping("{employeeId}/info")
    public Response<Void> updateEmployeeInfo(@PathVariable Long employeeId, @Valid @RequestBody EmployeeInfoRequest request){
        employeeService.updateEmployeeInfo(employeeId, request);
        return Response.success();
    }

    @PutMapping("{employeeId}/email")
    public Response<Void> updateEmail(@PathVariable Long employeeId, @Valid @RequestBody EmailUpdateRequest request){
        employeeService.updateEmail(employeeId, request);
        return Response.success();
    }

    @PutMapping("{employeeId}/password")
    public Response<Void> updatePassword(@PathVariable Long employeeId, @Valid @RequestBody PasswordUpdateRequest request){
        employeeService.updatePassword(employeeId, request);
        return Response.success();
    }

}
