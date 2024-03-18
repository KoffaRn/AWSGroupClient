package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDTO {
    private Integer companyId;
    private String companyName;
    private City city;
    private ArrayList<Employee> employees;
}
