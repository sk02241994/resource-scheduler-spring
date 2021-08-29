package com.office.resourcescheduler.config;

import org.springframework.stereotype.*;
import org.springframework.boot.web.server.*;
import org.springframework.boot.web.embedded.tomcat.*;
import com.office.resourcescheduler.util.Constants;

@Component
public class CustomContainer implements 
  WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setContextPath(Constants.RESOURCE_SCHEDULER);
    }
}
