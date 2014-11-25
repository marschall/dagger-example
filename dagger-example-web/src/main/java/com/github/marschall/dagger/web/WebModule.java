package com.github.marschall.dagger.web;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.github.marschall.dagger.bricks.jdbc.TransactionModule;
import com.github.marschall.dagger.service.DefaultInfostoreService;
import com.github.marschall.dagger.service.InfostoreModule;

import dagger.Module;
import dagger.Provides;

@Module(
  injects = DaggerFilter.class,
  includes = {TransactionModule.class, InfostoreModule.class})
public class WebModule {
  
  @Provides
  public DataSource provideDataSource() {
    return new NullDataSource();
  }
  
  static final class NullDataSource implements DataSource {

    @Override
    public PrintWriter getLogWriter() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
      // TODO Auto-generated method stub
      
    }

    @Override
    public int getLoginTimeout() throws SQLException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Connection getConnection(String username, String password)
        throws SQLException {
      // TODO Auto-generated method stub
      return null;
    }
    
  }

}
