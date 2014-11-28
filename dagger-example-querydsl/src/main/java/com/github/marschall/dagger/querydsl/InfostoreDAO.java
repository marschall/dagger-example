package com.github.marschall.dagger.querydsl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.jdbc.core.JdbcOperations;

import com.github.marschall.dagger.querydsl.domain.QEmployees;
import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

@Singleton
public class InfostoreDAO {

  private static final BigDecimal PLACE_HOLDER = BigDecimal.ZERO;
  private final JdbcOperations jdbcTemplate;
  private final SQLTemplates dialect;

  @Inject
  public InfostoreDAO(JdbcOperations jdbcTemplate, SQLTemplates dialect) {
    this.jdbcTemplate = jdbcTemplate;
    this.dialect = dialect;

  }

  public Employee getEmployee(long employeeId) {
    SQLQuery query = new SQLQuery(dialect);
    QEmployees e = new QEmployees("e");
    SQLBindings bindings = query.from(e).where(e.employeeId.eq(PLACE_HOLDER)).getSQL(e.all());

    return this.jdbcTemplate.queryForObject(bindings.getSQL(), this::readEmployee, employeeId);
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

  public void initializeDatabase() {
    this.jdbcTemplate.update("CREATE TABLE employees (" 
        + "   employee_id   NUMERIC       NOT NULL,"
        + "   first_name    VARCHAR(1000) NOT NULL,"
        + "   last_name     VARCHAR(1000) NOT NULL,"
        + "   date_of_birth DATE                  ,"
        + "   phone_number  VARCHAR(1000) NOT NULL,"
        + "   PRIMARY KEY (employee_id)"
        + ")");
    this.jdbcTemplate.update(
        "INSERT INTO employees (employee_id, first_name, last_name, date_of_birth    , phone_number)" 
            + "       VALUES         (1          , 'John'    , 'Doe'    , DATE '1980-01-01', '1234567890')");
  }

}
