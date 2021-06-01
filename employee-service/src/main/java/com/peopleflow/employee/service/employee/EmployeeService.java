package com.peopleflow.employee.service.employee;


import com.peopleflow.lib.EmployeeDto;

public interface EmployeeService {

    EmployeeDto get(String id);

    EmployeeDto create(EmployeeDto employee);

    EmployeeDto updateStatus(EmployeeDto dto);

}
