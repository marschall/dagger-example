package com.github.marschall.dagger.bricks.jdbc;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

import dagger.Module;
import dagger.Provides;

@Module(
  library = true,
  complete = false)
public class TransactionModule {
  
  @Singleton
  @Provides
  public PlatformTransactionManager provideTransactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
  
  @Singleton
  @Provides
  public TransactionOperations provideTransactionTemplate(PlatformTransactionManager transactionManager) {
    return new TransactionTemplate(transactionManager);
  }

}
