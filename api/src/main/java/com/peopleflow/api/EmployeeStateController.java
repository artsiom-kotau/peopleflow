package com.peopleflow.api;

import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.response.EmployeeStateResponse;
import com.peopleflow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeStateController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}/status")
    public EmployeeStateResponse employeeStatus(@PathVariable String id) {
        EmployeeState state = employeeService.status(id);
        return new EmployeeStateResponse(id, state.getLabel());
    }

    @PostMapping("/employee/{id}/checking")
    public EmployeeStateResponse check(@PathVariable String id) {
        EmployeeState state = employeeService.check(id);
        return createResponse(id, state);
    }

    @PostMapping("/employee/{id}/approve")
    public EmployeeStateResponse toApprove(@PathVariable String id) {
        EmployeeState state = employeeService.approve(id);
        return createResponse(id, state);
    }

    @PostMapping("/employee/{id}/activate")
    public EmployeeStateResponse activate(@PathVariable String id) {
        EmployeeState state = employeeService.activate(id);
        return createResponse(id, state);
    }

    private EmployeeStateResponse createResponse(String employeeId, EmployeeState state) {
        return new EmployeeStateResponse(employeeId, state.getLabel());
    }
}
