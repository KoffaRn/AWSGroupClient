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
public class Company {
    private int companyId;
    private String companyName;
    private City city;
    private List<Employee> employees;
}