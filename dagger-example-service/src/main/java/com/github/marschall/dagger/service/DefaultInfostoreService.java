package com.github.marschall.dagger.service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultInfostoreService implements InfostoreService {
  
  private final InfostoreDAO infostoreDAO;

  @Inject
  public DefaultInfostoreService(InfostoreDAO infostoreDAO) {
    this.infostoreDAO = infostoreDAO;
  }

  @Override
  public String getInfostoreVersion() {
    return "1.2.3";
  }
  
  @Override
  public Employee getEmployee(long employeeId) {
    return this.infostoreDAO.getEmployee(employeeId);
  }

  @Override
  public void initializeDatabase() {
    this.infostoreDAO.initializeDatabase();
    
  }

}
