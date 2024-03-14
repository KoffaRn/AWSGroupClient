package org.koffa.model;

import java.util.List;

import lombok.Data;

@Data
public class City {
    private long id;
    private String cityName;
    private List<Employee> employees;

}
