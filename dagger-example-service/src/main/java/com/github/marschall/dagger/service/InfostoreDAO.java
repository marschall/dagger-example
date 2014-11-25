package com.github.marschall.dagger.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.jdbc.core.JdbcOperations;

@Singleton
public class InfostoreDAO {
  
  private final JdbcOperations jdbcTemplate;

  @Inject
  public InfostoreDAO(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    
  }

}
