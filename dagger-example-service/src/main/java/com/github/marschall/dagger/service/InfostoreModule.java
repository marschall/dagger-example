package com.github.marschall.dagger.service;

import com.github.marschall.dagger.bricks.jdbc.JdbcModule;

import dagger.Module;

@Module(
  injects = DefaultInfostoreService.class,
  complete = false,
  includes = JdbcModule.class)
public class InfostoreModule {

}
