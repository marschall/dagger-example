package com.github.marschall.dagger.bricks;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class TransactionModule {
  
  @Singleton
  @Provides
  public PlatformTransactionManager provideTransactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
