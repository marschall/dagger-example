package com.github.marschall.dagger.service;

import dagger.Module;

@Module(
  injects = DefaultInfostoreService.class,
  complete = false,
  includes = JdbcModule.class)
public class InfostoreModule {

}
