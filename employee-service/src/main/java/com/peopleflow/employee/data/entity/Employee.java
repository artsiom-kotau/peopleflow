package com.peopleflow.employee.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "age")
    Integer age;

    @Column(name = "position")
    String position;

    @Column(name = "job_description")
    String jobDescription;

    @Column(name = "state")
    String state;
}
