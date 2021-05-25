package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.EmployeeRepository;
import com.peopleflow.employee.entity.Employee;
import com.peopleflow.exception.EmployeeServiceException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Setter
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeMapper employeeMapper;

    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto create(EmployeeDto employee) {
        try {
            Employee entity = employeeMapper.toEntity(employee);
            entity.setState(EmployeeState.ADDED);
            entity = employeeRepository.save(entity);
            return employeeMapper.toDto(entity);
        }catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            throw new EmployeeServiceException(exc);
        }

    }
}
