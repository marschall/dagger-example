package com.github.marschall.dagger.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.marschall.dagger.bricks.jdbc.TransactionModule;
import com.github.marschall.dagger.bricks.test.DaggerDatabaseRule;

import dagger.Module;
import dagger.Provides;

public class DefaultInfostoreServiceTest {

  @Inject
  InfostoreService infostoreService;

  @Rule
  public DaggerDatabaseRule daggerRule = new DaggerDatabaseRule(new TestModule());


  @Test
  public void version() {
    assertEquals("version", "1.2.3", infostoreService.getInfostoreVersion());
  }

  @Test
  public void getEmployeePresent() {
    Employee employee = infostoreService.getEmployee(1L);
    assertEquals("employee_id", 1L, employee.getEmployeeId());
    assertEquals("date_of_birth", LocalDate.of(1980, 1, 1), employee.getDateOfBirth());
  }
  
  @Test(expected = EmptyResultDataAccessException.class)
  public void getEmployeeNotPresent() {
    infostoreService.getEmployee(-1L);
  }

  @Module(
      includes = {InfostoreModule.class, TransactionModule.class},
      injects = {
          DefaultInfostoreServiceTest.class,
          PlatformTransactionManager.class, // for rule
          EmbeddedDatabase.class // for rule
          })
  static class TestModule {

    @Singleton
    @Provides
    public EmbeddedDatabase provideEmbeddedDatabase() {
      return new EmbeddedDatabaseBuilder()
      .setType(H2)
      .addDefaultScripts() // adds "schema.sql" and "data.sql"
      .build();
    }
    
    @Singleton
    @Provides
    public DataSource provideDataSource(EmbeddedDatabase database) {
      return database;
    }

  }

}
