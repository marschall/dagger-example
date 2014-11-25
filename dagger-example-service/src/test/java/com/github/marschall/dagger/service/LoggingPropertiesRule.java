package com.github.marschall.dagger.service;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class LoggingPropertiesRule implements MethodRule {

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        base.evaluate();
      }
    };
  }

}
