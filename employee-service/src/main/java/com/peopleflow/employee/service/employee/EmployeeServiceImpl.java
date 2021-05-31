package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.EmployeeRepository;
import com.peopleflow.employee.entity.Employee;
import com.peopleflow.employee.exception.EmployeeServiceException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Setter
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto get(String id) {
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            return employee.map(value -> employeeMapper.toDto(value)).orElse(null);
        } catch (RuntimeException exc) {
            log.error(exc.getMessage(), exc);
            throw new EmployeeServiceException(exc);
        }
    }

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

    @Override
    @Transactional
    public EmployeeDto updateStatus(EmployeeDto dto) {
        try {
            Optional<Employee> employee = employeeRepository.findById(dto.getId());
            if (employee.isEmpty()) {
                throw new EmployeeServiceException(String.format("There is no employee with id '%s'", dto.getId()));
            }
            Employee entity = employee.get();
            entity.setState(dto.getState());
            entity = employeeRepository.save(entity);
            return employeeMapper.toDto(entity);
        } catch (EmployeeServiceException exc)  {
            throw exc;
        } catch (RuntimeException exc) {
            log.error(exc.getMessage(), exc);
            throw new EmployeeServiceException(exc);
        }
    }
}
