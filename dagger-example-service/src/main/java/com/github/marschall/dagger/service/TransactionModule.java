package com.github.marschall.dagger.service;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class TransactionModule {
  
  @Singleton
  @Provides
  public PlatformTransactionManager provideTransactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
