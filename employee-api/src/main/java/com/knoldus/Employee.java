package com.knoldus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Employee {
    int id;
    String name;
    String city;
    int age;
}
