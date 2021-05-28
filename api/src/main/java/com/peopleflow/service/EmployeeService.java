package com.peopleflow.service;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employee);

    EmployeeState updateState(String id, EmployeeState newState);
}
