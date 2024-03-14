package org.koffa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public int userId;
    public String username;
    public String password;
    public ArrayList<Authority> authorities;
    public Company company;
    public City city;
    public Employee employee;
    public boolean enabled;
    public boolean accountNonLocked;
    public boolean accountNonExpired;
    public boolean credentialsNonExpired;
}
