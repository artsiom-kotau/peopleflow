package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.entity.Employee;
import com.peopleflow.lib.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeDto dto);

    EmployeeDto toDto(Employee entity);

}
