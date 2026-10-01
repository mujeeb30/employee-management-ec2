package com.example.employee.dao;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
boolean existsByEmail(String email);

boolean existsByEmailAndIdNot(String email, Integer id);
  
}