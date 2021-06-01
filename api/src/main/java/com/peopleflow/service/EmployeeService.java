package com.peopleflow.service;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employee);

    EmployeeState check(String userId);

    EmployeeState approve(String userId);

    EmployeeState activate(String userId);

    EmployeeState status(String id);
}
