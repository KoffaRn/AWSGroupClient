package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;
    private Company company;
    private City city;
}
