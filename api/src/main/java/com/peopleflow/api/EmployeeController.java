package com.peopleflow.api;

import com.peopleflow.exception.EmployeeNotFoundException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.response.EmployeeResponse;
import com.peopleflow.model.response.EmployeeStateResponse;
import com.peopleflow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeDto employee) {
        EmployeeDto addedEmployee = employeeService.addEmployee(employee);
        return new EmployeeResponse(addedEmployee.getId());
    }

    @PostMapping("/employee/{id}/check")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeStateResponse check(@PathVariable String id) {
        EmployeeState state = employeeService.check(id);
        return createResponse(id, state);
    }

    @PostMapping("/employee/{id}/approve")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeStateResponse toApprove(@PathVariable String id) {
        EmployeeState state = employeeService.approve(id);
        return createResponse(id, state);
    }

    @PostMapping("/employee/{id}/activate")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeStateResponse activate(@PathVariable String id) {
        EmployeeState state = employeeService.activate(id);
        return createResponse(id, state);
    }

    @GetMapping("/employee/{id}/status")
    public EmployeeStateResponse status(@PathVariable String id) {
        EmployeeState state = employeeService.status(id);
        if (state == null) {
            throw new EmployeeNotFoundException(id);
        }
        return createResponse(id, state);
    }

    private EmployeeStateResponse createResponse(String employeeId, EmployeeState state) {
        return new EmployeeStateResponse(employeeId, state);
    }

}
