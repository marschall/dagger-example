package com.github.marschall.dagger.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

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
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DaggerFilter implements Filter {

  @Inject // -> enables compile time checking
  volatile InfostoreService infostoreService;

  @Inject // -> enables compile time checking
  volatile TransactionOperations transactionTemplate;

  @Inject // -> enables compile time checking
  volatile Configuration configuration;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    ObjectGraph graph = ObjectGraph.create(WebModule.class);
    graph.inject(this);
    // work around that we can't have space in Docker ENV vars
    this.transactionTemplate.execute((status) -> {
      this.infostoreService.initializeDatabase();
      return null; });
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
        case "/":
          renderIndex(response, pathInfo);
          break;
        case "/jce":
          renderJce(response);
          break;
        case "/java2d":
          response.setContentType("image/png");
          ImageDrawer.drawImage(response.getOutputStream());
          break;
        case "/font-families":
          renderFontFamilies(response);
          break;
        case "/employee":
          renderEmployee(response);
          break;
        default:
          renderIndex(response, pathInfo);
          break;
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  private void renderIndex(ServletResponse response, String contextPath) throws IOException, ServletException {
    Map<String, Object> root = Collections.singletonMap("contextPath", contextPath);
    renderTemplate(response, "index.ftl", root);
  }

  private void renderJce(ServletResponse response) throws IOException, ServletException {
    int keySize = getKeySize();
    Map<String, Object> root = Collections.singletonMap("keySize", keySize);
    renderTemplate(response, "jce.ftl", root);
  }
  
  private void renderEmployee(ServletResponse response) throws IOException, ServletException {
    Employee employee = this.transactionTemplate.execute((status) -> this.infostoreService.getEmployee(1L));
    Map<String, Object> root = Collections.singletonMap("employee", employee);
    renderTemplate(response, "employee.ftl", root);
  }
  
  private void renderFontFamilies(ServletResponse response) throws IOException, ServletException {
    Map<String, Object> root = Collections.singletonMap("fontFamilies", ImageDrawer.getFontFamilyNames());
    renderTemplate(response, "font-families.ftl", root);
  }

  private void renderTemplate(ServletResponse response, String tempalteName, Object dataModel)
      throws IOException, ServletException {
    response.setContentType("text/html");
    response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
    try {
      Template template = configuration.getTemplate(tempalteName);
      template.process(dataModel, response.getWriter());
    } catch (TemplateException e) {
      throw new ServletException("could not render template", e);
    }
  }

  private int getKeySize() {
    try {
      return Cipher.getMaxAllowedKeyLength("AES");
    } catch (NoSuchAlgorithmException e) {
      return -1;
    }
  }

  private boolean isServiceRequest(ServletRequest request) {
    return true;
  }

}
