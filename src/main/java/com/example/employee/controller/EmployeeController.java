package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from the frontend running on localhost:5173
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable Integer id) {

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        return ResponseEntity.ok(employee);
    }

    // CREATE EMPLOYEE
    @PostMapping
    public ResponseEntity<?> createEmployee(
            @RequestBody Employee employee) {

        // First name validation
        if (employee.getFirstName() == null ||
                employee.getFirstName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter first name");
        }

        String nameRegex = "^[A-Za-z ]+$";

        if (!employee.getFirstName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("First name should contain only letters");
        }

        // Last name validation
        if (employee.getLastName() == null ||
                employee.getLastName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter last name");
        }

        if (!employee.getLastName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Last name should contain only letters");
        }

        // Email validation
if (employee.getEmail() == null ||
        employee.getEmail().trim().isEmpty()) {

    return ResponseEntity
            .badRequest()
            .body("Please enter email");
}

String emailRegex =
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

if (!employee.getEmail().matches(emailRegex)) {

    return ResponseEntity
            .badRequest()
            .body("Please enter a proper email");
}

// Check duplicate email
if (employeeService.existsByEmail(employee.getEmail())) {

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body("Employee with this email already exists");
}

        // Salary validation
        if (employee.getSalary() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter salary");
        }

        if (employee.getSalary() <= 0) {

            return ResponseEntity
                    .badRequest()
                    .body("Salary must be greater than 0");
        }

        Employee savedEmployee =
                employeeService.createEmployee(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEmployee);
    }

    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee employee) {

        Employee existingEmployee =
                employeeService.getEmployeeById(id);

        if (existingEmployee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        String nameRegex = "^[A-Za-z ]+$";

        // First name
        if (employee.getFirstName() == null ||
                employee.getFirstName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter first name");
        }

        if (!employee.getFirstName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("First name should contain only letters");
        }

        // Last name
        if (employee.getLastName() == null ||
                employee.getLastName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter last name");
        }

        if (!employee.getLastName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Last name should contain only letters");
        }

        // Email
        if (employee.getEmail() == null ||
                employee.getEmail().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter email");
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!employee.getEmail().matches(emailRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter a proper email");
        }

        // Department
        if (employee.getDepartment() == null ||
                employee.getDepartment().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select a department");
        }

        // Salary
        if (employee.getSalary() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter salary");
        }

        if (employee.getSalary() <= 0) {

            return ResponseEntity
                    .badRequest()
                    .body("Salary must be greater than 0");
        }

        Employee updatedEmployee =
                employeeService.updateEmployee(id, employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Integer id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                "Employee deleted successfully"
        );
    }
}