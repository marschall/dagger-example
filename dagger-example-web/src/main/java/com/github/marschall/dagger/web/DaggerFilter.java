package com.github.marschall.dagger.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.github.marschall.dagger.service.InfostoreService;

import dagger.ObjectGraph;

public class DaggerFilter implements Filter {

  @Inject // -> enables compile time checking
  private volatile InfostoreService infostoreService;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    ObjectGraph graph = ObjectGraph.create(WebModule.class);
    graph.inject(this);
  }
  
  @Override
  public void destroy() {
    // unfortunately no close possible
    infostoreService = null;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    chain.doFilter(request, response);
  }

}
