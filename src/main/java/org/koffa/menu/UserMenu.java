package org.koffa.menu;

import org.koffa.model.City;
import org.koffa.model.EmployeeDTO;
import org.koffa.service.CityService;
import org.koffa.service.EmployeeService;

import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final String URL = "http://localhost:5000"; //Todo add URL from the properties
    private final String JWT;
    private final String username;
    Scanner scanner = new Scanner(System.in);

    private EmployeeService employeeService;
    private CityService cityService;

    public UserMenu(String JWT, String username) {
        this.JWT = JWT;
        this.username = username;
        this.cityService = new CityService(URL);
        this.employeeService = new EmployeeService(URL);
    }

    public void showMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("Welcome " + username + ", You are logged in as a user");
        System.out.println("1. View Cities");
        System.out.println("2. View Employees");
        System.out.println("3. Logout");
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                viewCities();
                break;
            case 2:
                viewEmployees();
                break;
            case 3:
                logout();
                break;
        }
    }

    private void logout() {
        System.exit(0);
        System.out.println("Logged out");
    }

    private void viewCities() {
        System.out.println("--------------- Welcome to City View ---------------");
        System.out.println("1. Get all Cities");
        System.out.println("2. Go back");
        int input = scanner.nextInt();
        if (input == 2) showMenu();

        switch (input) {
            case 1:
                getAllCities();
                viewCities();
                break;
        }
    }

    public void getAllCities() {
        List<City> cities = cityService.getAllCities(JWT);
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }

    private void viewEmployees() {
        System.out.println("--------------- Welcome to Employee View ---------------");
        System.out.println("1. Get all Employees");
        System.out.println("2. Go back");
        int input = scanner.nextInt();
        if (input == 2) showMenu();

        switch (input) {
            case 1:
                getAllEmployees();
                viewEmployees();
                break;
        }
    }

    public void getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        for (EmployeeDTO employee : employees) {
            System.out.println(employee.toString());
        }
    }
}
