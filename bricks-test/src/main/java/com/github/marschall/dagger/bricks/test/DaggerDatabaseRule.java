package com.github.marschall.dagger.bricks.test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import dagger.ObjectGraph;

/**
 * A simple rule that injects a test case and runs all tests in a transaction
 * that is rolled back.
 */
public class DaggerDatabaseRule implements MethodRule {

  private Object[] modules;

  public DaggerDatabaseRule(Object... modules) {
    this.modules = modules;
  }

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        ObjectGraph graph = ObjectGraph.create(modules);
        graph.inject(target);
        
        EmbeddedDatabase database;
        try {
          database = graph.get(EmbeddedDatabase.class);
        } catch (IllegalArgumentException e) {
          // that's fine
          database = null;
        }

        PlatformTransactionManager transactionManager = graph.get(PlatformTransactionManager.class);
        TransactionStatus status = transactionManager.getTransaction(null);
        try {
          base.evaluate();
        } finally {
          transactionManager.rollback(status);
          if (database != null) {
            database.shutdown();
          }
        }

      }
    };
  }

}
