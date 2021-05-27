package com.peopleflow.api;

import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.request.Employee;
import com.peopleflow.model.request.EmployeeStateRequest;
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
    public EmployeeResponse addEmployee(Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new EmployeeResponse(addedEmployee.getId());
    }

    @PostMapping("/employee/{id}/updateStatus")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeStateResponse updateStatus(@PathVariable String id, @RequestBody EmployeeStateRequest request) {
        EmployeeState state = employeeService.updateState(id, request.getState());
        return new EmployeeStateResponse(id, state);
    }

}
