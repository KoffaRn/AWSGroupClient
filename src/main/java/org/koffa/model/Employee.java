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



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee ID: ").append(employeeId).append("\n");
        sb.append("First Name: ").append(firstName).append("\n");
        sb.append("Last Name: ").append(lastName).append("\n");
        sb.append("Job Title: ").append(jobTitle).append("\n");
        sb.append("Salary: ").append(salary).append("\n");
        sb.append("Company: ").append(company).append("\n");
        sb.append("City: ").append(city).append("\n");
        return sb.toString();
    }
}


