package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private int companyId;
    private String companyName;
    private City city;
    private ArrayList<String> employees;
}
