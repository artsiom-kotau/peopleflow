package com.peopleflow.api;

import com.peopleflow.model.request.Employee;
import com.peopleflow.model.response.EmployeeResponse;
import com.peopleflow.service.EmployeeService;
import com.peopleflow.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeResponse addEmployee(Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new EmployeeResponse(addedEmployee.getId());
    }

}
