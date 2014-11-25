package com.github.marschall.dagger.service;

import com.github.marschall.dagger.bricks.jdbc.JdbcModule;

import dagger.Module;
import dagger.Provides;

@Module(
  complete = false,
  library=true,
  includes = JdbcModule.class)
public class InfostoreModule {

  @Provides
  public InfostoreService provideInfostoreService(InfostoreDAO infostoreDAO) {
    return new DefaultInfostoreService(infostoreDAO);
  }
  
}
