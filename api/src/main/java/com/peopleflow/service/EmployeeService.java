package com.peopleflow.service;

import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.request.Employee;

import java.util.Optional;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    EmployeeState updateState(String id, EmployeeState newState);
}
