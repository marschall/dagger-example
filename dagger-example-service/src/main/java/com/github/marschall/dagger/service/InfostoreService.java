package com.github.marschall.dagger.service;

public interface InfostoreService {

  
  String getInfostoreVersion();
  
  Employee getEmployee(long employeeId);
  
  void initializeDatabase();
  
}
