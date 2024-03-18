package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;
    private City city;
    private Company company;

    List<User> users;


    @Override
    public String toString() {
        return "Employee ID: " + employeeId + "\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Job Title: " + jobTitle + "\n" +
                "Salary: " + salary + "\n" +
                "City: " + city + "\n";
        }
}



