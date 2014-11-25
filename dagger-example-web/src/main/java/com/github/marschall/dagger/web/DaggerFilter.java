package com.github.marschall.dagger.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.transaction.support.TransactionOperations;

import com.github.marschall.dagger.service.InfostoreService;

import dagger.ObjectGraph;

public class DaggerFilter implements Filter {

  @Inject // -> enables compile time checking
  volatile InfostoreService infostoreService;
  
  @Inject // -> enables compile time checking
  volatile TransactionOperations transactionTemplate;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    try {
      ObjectGraph graph = ObjectGraph.create(WebModule.class);
      graph.inject(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void destroy() {
    // unfortunately no close possible
    infostoreService = null;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (this.isServiceRequest(request)) {
      this.transactionTemplate.execute((status) -> this.infostoreService.getInfostoreVersion());
      sendResponse(response);
    } else {
      chain.doFilter(request, response);
    }
  }
  
  private void sendResponse(ServletResponse response) throws IOException {
    response.setContentType("text/plain");
    response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
    response.getWriter().write("OK");
  }
  
  private boolean isServiceRequest(ServletRequest request) {
    return true;
  }

}
