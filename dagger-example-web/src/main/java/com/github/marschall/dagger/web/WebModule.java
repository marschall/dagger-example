package com.github.marschall.dagger.web;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.github.marschall.dagger.bricks.jdbc.TransactionModule;
import com.github.marschall.dagger.service.InfostoreModule;

import dagger.Module;
import dagger.Provides;

@Module(
  injects = DaggerFilter.class,
  includes = {TransactionModule.class, InfostoreModule.class})
public class WebModule {
  
  @Provides
  public DataSource provideDataSource() throws NamingException {
    InitialContext initialContext = new InitialContext();
    return (DataSource) initialContext.lookup("jdbc/dagger");
  }
  
}
