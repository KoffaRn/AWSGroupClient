package org.koffa.menu;

import org.koffa.service.CityService;
import org.koffa.service.CompanyService;
import org.koffa.service.EmployeeService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class AdminMenu {

    private final String URL = null; //Todo add URL from the properties
    private final String JWT;
    private final String username;
    Scanner scanner = new Scanner(System.in);
    
    public AdminMenu (String JWT, String username) {
        this.JWT = JWT;
        this.username = username;
        
    }


    public void showMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("Welcome " + username + "You are logged in as an admin");
        System.out.println("1. Control Users");
        System.out.println("2. Control Companies");
        System.out.println("3. Control Employees");
        System.out.println("4. Control Citys");
        System.out.println("5. Logout");
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                controlUsers();
                break;
            case 2:
                controlCompanies();
                break;
            case 3:
                controlEmployees();
                break;
            case 4:
                controlCitys();
                break;
            case 5:
                logout();
                break;
        }
        
        
        
        
    }

    private void logout() {
        System.exit(0);
        System.out.println("Logged out");
    }

    private void controlCitys() {
        CityService cityService = new CityService(URL);
        System.out.println("--------------- Welcome to the City control ---------------");
        System.out.println("1. Add City");
        System.out.println("2. Update City");
        System.out.println("3. Get City by name");
        System.out.println("4. Delete City");
        System.out.println("5. Get all Citys");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();


    }

    private void controlEmployees() {

        EmployeeService employeeService = new EmployeeService(URL);
        System.out.println("--------------- Welcome to the Employee control ---------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Get Employee by name");
        System.out.println("4. Delete Employee");
        System.out.println("5. Get all Employees");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();


    }

    private void controlCompanies() {
        CompanyService companyService = new CompanyService(URL);
        System.out.println("--------------- Welcome to the Company control ---------------");
        System.out.println("1. Add Company");
        System.out.println("2. Update Company");
        System.out.println("3. Get Company by name");
        System.out.println("4. Delete Company");
        System.out.println("5. Delete Company");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();



    }

    private void controlUsers() {
        System.out.println("--------------- Welcome to the User control ---------------");
        System.out.println("1. Add User");
        System.out.println("2. Update User");
        System.out.println("3. Get User by name");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();



    }


}
