package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import java.util.List;

public interface EmployeeService extends Service {

    @Override
    default Descriptor descriptor() {
        return Service.named("employee").withCalls(
                Service.restCall(Method.GET, "/api/get/:id", this::getEmployee),
                Service.restCall(Method.GET,"/api/getAllEmployee", this::getEmployeeList),
                Service.restCall(Method.POST,"/api/post", this::postEmployee),
                Service.restCall(Method.DELETE,"/api/delete/:id", this::deleteEmployee),
               Service.restCall(Method.PUT,"/api/put/", this::putEmployee)
        ).withAutoAcl(true);
    }

    ServiceCall<NotUsed, Employee> getEmployee(int id);
    ServiceCall<NotUsed, List<Employee>> getEmployeeList();
    ServiceCall<Employee, String> postEmployee();
    ServiceCall<NotUsed, String> deleteEmployee(int id);
    ServiceCall<Employee, String> putEmployee();
}
