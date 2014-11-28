package com.github.marschall.dagger.querydsl;

import javax.inject.Singleton;

import com.github.marschall.dagger.bricks.jdbc.JdbcModule;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.SQLTemplates;

import dagger.Module;
import dagger.Provides;

@Module(
  complete = false,
  library=true,
  includes = JdbcModule.class,
  injects = InfostoreDAO.class)
public class InfostoreModule {

  @Singleton
  @Provides
  public SQLTemplates provideTemplates() {
    return new H2Templates();
  }
  
}
