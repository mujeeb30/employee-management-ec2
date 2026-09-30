package com.example.employee.service;

import com.example.employee.dao.EmployeeDao;
import com.example.employee.entity.Employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {

        return employeeDao.findById(id).orElse(null);
    }

    @Override
    public Employee createEmployee(Employee employee) {

        return employeeDao.save(employee);
    }

    @Override
    public Employee updateEmployee(
            Integer id,
            Employee employee) {

        Employee existingEmployee =
                employeeDao.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setFirstName(
                employee.getFirstName());

        existingEmployee.setLastName(
                employee.getLastName());

        existingEmployee.setEmail(
                employee.getEmail());

        existingEmployee.setDepartment(
                employee.getDepartment());

        existingEmployee.setSalary(
                employee.getSalary());

        return employeeDao.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Integer id) {

        employeeDao.deleteById(id);
    }
    @Override
public boolean existsByEmail(String email) {
    return employeeDao.existsByEmail(email);
}

    @Override
public Employee patchEmployee(Integer id, Employee employee) {

    Employee existingEmployee =
            employeeDao.findById(id).orElse(null);

    if (existingEmployee == null) {
        return null;
    }

    if (employee.getFirstName() != null) {
        existingEmployee.setFirstName(employee.getFirstName());
    }

    if (employee.getLastName() != null) {
        existingEmployee.setLastName(employee.getLastName());
    }

    if (employee.getEmail() != null) {
        existingEmployee.setEmail(employee.getEmail());
    }

    if (employee.getDepartment() != null) {
        existingEmployee.setDepartment(employee.getDepartment());
    }

    if (employee.getSalary() != null) {
        existingEmployee.setSalary(employee.getSalary());
    }

    return employeeDao.save(existingEmployee);
}
}