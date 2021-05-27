package com.peopleflow.employee.service.employee;

import com.peopleflow.lib.EmployeeDto;

public interface EmployeeErrorProcessor {

    void process(EmployeeDto employee, Exception exc);
}
