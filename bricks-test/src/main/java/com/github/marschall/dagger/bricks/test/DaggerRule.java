package com.github.marschall.dagger.bricks.test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import dagger.ObjectGraph;

/**
 * A simple rule that injects a test case.
 */
public class DaggerRule implements MethodRule {

  private Object[] modules;

  public DaggerRule(Object... modules) {
    this.modules = modules;
  }

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        ObjectGraph graph = ObjectGraph.create(modules);
        graph.inject(target);

        base.evaluate();
      }
    };
  }

}
