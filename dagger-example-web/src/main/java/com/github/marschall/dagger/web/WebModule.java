package com.github.marschall.dagger.web;

import static freemarker.template.Configuration.VERSION_2_3_21;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;

import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.github.marschall.dagger.bricks.jdbc.TransactionModule;
import com.github.marschall.dagger.service.InfostoreModule;

import dagger.Module;
import dagger.Provides;
import freemarker.template.Configuration;

@Module(
  injects = DaggerFilter.class,
  includes = {TransactionModule.class, InfostoreModule.class})
public class WebModule {
  
  @Singleton
  @Provides
  public Configuration provideFreemarkerConfiguration() {
    Configuration configuration = new Configuration(VERSION_2_3_21);
    configuration.setClassForTemplateLoading(WebModule.class, "");
    configuration.setDefaultEncoding("UTF-8");
    configuration.setTemplateExceptionHandler(RETHROW_HANDLER);
    return configuration;
  }
  
  @Singleton
  @Provides
  public DataSource provideDataSource() {
    try {
      InitialContext initialContext = new InitialContext();
      Context envContext  = (Context) initialContext.lookup("java:/comp/env");
      return (DataSource) envContext.lookup("jdbc/dagger");
    } catch (NamingException e) {
      throw new RuntimeException("failed to look up data source", e);
    }
  }
  
}
