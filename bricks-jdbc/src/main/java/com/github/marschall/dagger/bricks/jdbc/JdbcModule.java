package com.github.marschall.dagger.bricks.jdbc;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import dagger.Module;
import dagger.Provides;

@Module(
  library = true,
  complete = false)
public class JdbcModule {
  
  @Singleton
  @Provides
  public JdbcOperations provideJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
