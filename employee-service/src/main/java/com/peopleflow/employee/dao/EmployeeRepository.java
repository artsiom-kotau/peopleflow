package com.peopleflow.employee.dao;

import com.peopleflow.employee.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
}
