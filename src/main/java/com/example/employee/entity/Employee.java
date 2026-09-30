package com.example.employee.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id", length = 36, unique = true, nullable = false)
    private String employeeId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private Double salary;

    public Employee() {
    }
// generate a unique employeeId before persisting the entity
    @PrePersist
    public void generateEmployeeId() {
        if (employeeId == null) {
            employeeId = UUID.randomUUID().toString();
        }
    }
//using getters and setters for all the fields
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}