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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User ID: ").append(userId).append("\n");
        sb.append("Username: ").append(username).append("\n");
        sb.append("Authorities: ").append(authorities).append("\n");
        sb.append("Company: ").append(company).append("\n");
        sb.append("City: ").append(city).append("\n");
        sb.append("Employee: ").append(employee).append("\n");
        return sb.toString();
    }
}
