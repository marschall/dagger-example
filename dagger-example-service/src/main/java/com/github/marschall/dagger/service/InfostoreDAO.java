package com.github.marschall.dagger.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.jdbc.core.JdbcOperations;

@Singleton
public class InfostoreDAO {
  
  private static final String SELECT_BY_EMPLOYEE_ID =
      "SELECT employee_id, first_name, last_name, date_of_birth, phone_number "
      + " FROM employees "
      + " WHERE employee_id = ?";
  private final JdbcOperations jdbcTemplate;

  @Inject
  public InfostoreDAO(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    
  }
  
  public Employee getEmployee(long employeeId) {
    return this.jdbcTemplate.queryForObject(SELECT_BY_EMPLOYEE_ID, this::readEmployee, employeeId);
  }
  
  private Employee readEmployee(ResultSet resultSet, int rowNum) throws SQLException {
    Employee employee = new Employee();
    employee.setEmployeeId(resultSet.getLong("employee_id"));
    employee.setFirstName(resultSet.getString("first_name"));
    employee.setLastName(resultSet.getString("last_name"));
    employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
    employee.setPhoneNumber(resultSet.getString("phone_number"));
    return employee;
  }

}
