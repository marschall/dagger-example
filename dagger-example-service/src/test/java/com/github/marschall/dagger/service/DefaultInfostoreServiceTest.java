package com.github.marschall.dagger.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.github.marschall.dagger.bricks.test.DaggerRule;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import dagger.Module;
import dagger.Provides;

public class DefaultInfostoreServiceTest {

  @Inject
  InfostoreService infostoreService;
  
  @Rule
  public DaggerRule daggerRule = new DaggerRule(new TestModule());
  
  
  @Test
  public void version() {
    assertEquals("version", "1.2.3", infostoreService.getInfostoreVersion());
  }
  
  @Module(includes = InfostoreModule.class,
      injects = DefaultInfostoreServiceTest.class)
  static class TestModule {
    
    @Provides
    public DataSource provideDataSource() {
      return new EmbeddedDatabaseBuilder()
        .setType(H2)
        .addDefaultScripts() // adds "schema.sql" and "data.sql"
        .build();
    }
    
  }

}
