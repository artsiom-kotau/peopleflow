package com.peopleflow.employee.service.state;

import com.peopleflow.lib.EmployeeDto;

public interface StateProcessor {

    void process(EmployeeDto dto);
}
