package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;
    private City city;
    private Company company;
}