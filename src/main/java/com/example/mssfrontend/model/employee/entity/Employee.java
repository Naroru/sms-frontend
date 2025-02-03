package com.example.mssfrontend.model.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Setter
@Getter
@ToString
public class Employee {

  @Id
  @GeneratedValue
  private Long id;

  private String firstName;

  private String lastName;

}
