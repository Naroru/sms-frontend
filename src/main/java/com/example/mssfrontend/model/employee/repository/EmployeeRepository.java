package com.example.mssfrontend.model.employee.repository;

import com.example.mssfrontend.model.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
