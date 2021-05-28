package com.peopleflow.lib;

import lombok.Data;

@Data
public class EmployeeDto {

    String id;

    String name;

    Integer age;

    String position;

    String jobDescription;

    EmployeeState state;
}
