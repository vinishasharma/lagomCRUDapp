package com.knoldus;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class EmployeeModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(EmployeeService.class, EmployeeServiceImpl.class);
    }

}
