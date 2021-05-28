package com.peopleflow.employee.dao;

import com.peopleflow.employee.entity.EmployeeError;
import org.springframework.data.repository.CrudRepository;

public interface ErrorRepository extends CrudRepository<EmployeeError, Long> {

}
