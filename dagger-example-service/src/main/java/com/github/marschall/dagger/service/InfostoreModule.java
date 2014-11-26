package com.github.marschall.dagger.service;

import javax.inject.Singleton;

import com.github.marschall.dagger.bricks.jdbc.JdbcModule;

import dagger.Module;
import dagger.Provides;

@Module(
  complete = false,
  library=true,
  includes = JdbcModule.class)
public class InfostoreModule {

  @Singleton
  @Provides
  public InfostoreService provideInfostoreService(DefaultInfostoreService service) {
    return service;
  }
  
}
