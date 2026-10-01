package com.example.employee.service;

import com.example.employee.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Integer id, Employee employee);

    Employee patchEmployee(Integer id, Employee employee);

    boolean existsByEmail(String email);

    void deleteEmployee(Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);
}