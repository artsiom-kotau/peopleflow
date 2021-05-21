package com.peopleflow.service;

import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.request.Employee;

import java.util.Optional;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    EmployeeState check(String userId);

    EmployeeState approve(String userId);

    EmployeeState activate(String userId);

    EmployeeState status(String id);
}
