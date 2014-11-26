package com.github.marschall.dagger.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.support.TransactionOperations;

import com.github.marschall.dagger.service.Employee;
import com.github.marschall.dagger.service.InfostoreService;

import dagger.ObjectGraph;

public class DaggerFilter implements Filter {

  @Inject // -> enables compile time checking
  volatile InfostoreService infostoreService;

  @Inject // -> enables compile time checking
  volatile TransactionOperations transactionTemplate;

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
    if (this.isServiceRequest(request)) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      String pathInfo = httpRequest.getPathInfo();
      if (pathInfo == null) {
        pathInfo = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
      }
      switch (pathInfo) {
        case "/jce":
          sendResponse(response, getKeySize());
          break;
        case "/java2d":
          response.setContentType("image/png");
          ImageDrawer.drawImage(response.getOutputStream());
          break;
        case "/font-families":
          String fontFamilies = ImageDrawer.getFontFamilyNames();
          sendResponse(response, fontFamilies);
          break;
        case "/employee":
          Employee employee = this.transactionTemplate.execute((status) -> this.infostoreService.getEmployee(1L));
          sendResponse(response, employee);
          break;
        default:
          sendResponse(response, "/jce\n/java2d\n/font-families\n/java2d\n/employee");
          break;
      }
    } else {
      chain.doFilter(request, response);
    }
  }
  
  private int getKeySize() {
    try {
      return Cipher.getMaxAllowedKeyLength("AES");
    } catch (NoSuchAlgorithmException e) {
      return -1;
    }
  }

  private void sendResponse(ServletResponse response, Object value) throws IOException {
    response.setContentType("text/plain");
    response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
    PrintWriter writer = response.getWriter();
    writer.write("OK");
    writer.write("\n");
    writer.write(value.toString());
  }

  private boolean isServiceRequest(ServletRequest request) {
    return true;
  }

}
