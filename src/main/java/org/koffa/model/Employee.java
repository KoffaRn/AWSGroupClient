package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long employeeId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;
    private City city;
    private Company company;

    List<User> users;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee ID: ").append(employeeId).append("\n");
        sb.append("First Name: ").append(firstName).append("\n");
        sb.append("Last Name: ").append(lastName).append("\n");
        sb.append("Job Title: ").append(jobTitle).append("\n");
        sb.append("Salary: ").append(salary).append("\n");
        sb.append("City: ").append(city).append("\n");
        return sb.toString();
    }
}



