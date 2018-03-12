package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
//import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class EmployeeServiceImpl implements EmployeeService {
    List<Employee> employeeList = new ArrayList<>();
    private List<Employee> getEmployeeData() {
        return employeeList;
    }

    @Override
    public ServiceCall<NotUsed, Employee> getEmployee(int id) {
        Optional<Employee> employeeOptional = getEmployeeData().stream()
                .filter(emp -> emp.getId() == id)
                .findFirst();
        return request -> CompletableFuture.completedFuture(employeeOptional
                .<NotFound>orElseThrow(() -> {throw new NotFound("Employee with id "+ id +" was not found.");}));
    }

    @Override
    public ServiceCall<NotUsed, List<Employee>> getEmployeeList() {
        return request -> CompletableFuture.completedFuture(
                getEmployeeData());
    }


    @Override
    public ServiceCall<Employee, String> postEmployee() {
        return request -> {
            boolean idExists = employeeList.stream()
                    .anyMatch(employee -> employee.getId() == request.getId());

            if (!idExists) {
                 employeeList.add(Employee.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .city(request.getCity())
                        .age(request.getAge())
                        .build());

                return CompletableFuture.completedFuture("employee added successfully");
            } else {
                return CompletableFuture.completedFuture("employee with same ID already exists");
            }
        };
    }

    @Override
    public ServiceCall<NotUsed, String> deleteEmployee(int id) {

        List<Employee> employeeList = getEmployeeData();
        boolean idExists = employeeList.stream()
                .anyMatch(employee -> employee.getId() == id);

        if (idExists) {
            employeeList.remove(employeeList.stream().filter(emp -> emp.getId() == id)
                    .collect(Collectors.toList()).get(0));
            return request -> CompletableFuture.completedFuture("employee deleted successfully");
        } else {
            return request -> CompletableFuture.completedFuture("Id is not present");
        }

    }

    @Override
    public ServiceCall<Employee, String> putEmployee() {
        return request -> {
            boolean idExists = employeeList.stream()
                    .anyMatch(employee -> employee.getId() == request.getId());
            if (idExists) {
                employeeList.
                        stream()
                        .filter(emp -> emp.getId() == request.getId())
                        .collect(Collectors.toList())
                        .get(0);
                employeeList.remove(employeeList.stream().filter(emp -> emp.getId() == request.getId())
                        .collect(Collectors.toList()).get(0));
                employeeList.add(Employee.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .city(request.getCity())
                        .age(request.getAge())
                        .build());
                return CompletableFuture.completedFuture("employee updated successfully");
            } else {
                return CompletableFuture.completedFuture("Id is not present");
            }

        };
    }
}


