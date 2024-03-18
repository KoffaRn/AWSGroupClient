package org.koffa.menu;

import org.koffa.model.City;
import org.koffa.model.Company;
import org.koffa.model.Employee;
import org.koffa.model.EmployeeDTO;
import org.koffa.service.CityService;
import org.koffa.service.EmployeeService;

import java.util.ArrayList;
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
        System.out.println("1. View all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Update employee");
        System.out.println("4. Delete employee");
        System.out.println("5. Logout");
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                getAllEmployees();
                showMenu();
            case 2:
                addEmployee();
                showMenu();
            case 3:
                updateEmployee();
                showMenu();
            case 4:
                deleteEmployee();
                showMenu();
            case 5:
                logout();
        }
    }

    private void deleteEmployee() {
        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployees(JWT);
        } catch (Exception e) {
            System.err.println("Failed to get employees: " + e.getMessage());
        }
        System.out.println("0. Back");
        for(int i = 0; i < employees.size(); i++) {
            System.out.println(i + 1 + ". " + employees.get(i).getFirstName() + " " + employees.get(i).getLastName() + " " + employees.get(i).getJobTitle() + " " + employees.get(i).getSalary() + " " + employees.get(i).getCity() + " " + employees.get(i).getLastName() + " " + employees.get(i).getCompany().getCompanyName());
        }
        System.out.println("Enter the number of the employee you want to delete");
        int input = scanner.nextInt();
        if(input == 0) {
            showMenu();
        }
        if(input > employees.size() || input < 1) {
            System.out.println("Invalid input");
            deleteEmployee();
        }
        try {
            employeeService.deleteEmployee(employees.get(input - 1).getId(), JWT);
        } catch (Exception e) {
            System.err.println("Failed to delete employee: " + e.getMessage());
        }
    }

    private void updateEmployee() {

        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployees(JWT);
        } catch (Exception e) {
            System.err.println("Failed to get employees: " + e.getMessage());
        }
        System.out.println("0. Back");
        for(int i = 0; i < employees.size(); i++) {
            System.out.println(i + 1 + ". " + employees.get(i).getFirstName() + " " + employees.get(i).getLastName() + " " + employees.get(i).getJobTitle() + " " + employees.get(i).getSalary() + " " + employees.get(i).getCity() + " " + employees.get(i).getLastName() + " " + employees.get(i).getCompany().getCompanyName());
        }
        System.out.println("Enter the number of the employee you want to update");
        int input = scanner.nextInt();
        if(input == 0) {
            showMenu();
        }
        if(input > employees.size() || input < 1) {
            System.out.println("Invalid input");
            updateEmployee();
        }
        EmployeeDTO employee = employees.get(input - 1);
        System.out.println("Enter new first name:");
        employee.setFirstName(scanner.next());
        System.out.println("Enter new last name");
        employee.setLastName(scanner.next());
        System.out.println("Enter new job title");
        employee.setJobTitle(scanner.next());
        System.out.println("Enter new salary (leave blank if you don't want to change)");
        employee.setSalary(getDoubleInput());
        Employee updatedEmployee = Employee.builder()
                .employeeId(Long.valueOf(employee.getId()))
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobTitle(employee.getJobTitle())
                .salary(employee.getSalary())
                .city(employee.getCity())
                .company(employee.getCompany())
                .build();
        try {
            employeeService.updateEmployee(employee.getId(), updatedEmployee, JWT);
        } catch (Exception e) {
            System.err.println("Failed to update employee: " + e.getMessage());
        }
    }

    private void addEmployee() {
        Employee employee = new Employee();
        System.out.println("Enter employee first name");
        employee.setFirstName(scanner.next());
        System.out.println("Enter employee last name");
        employee.setLastName(scanner.next());
        System.out.println("Enter employee job title");
        employee.setJobTitle(scanner.next());
        System.out.println("Enter employee salary");
        employee.setSalary(getDoubleInput());
        System.out.println("Enter city");
        employee.setCity(City.builder().cityName(scanner.next()).build());
        System.out.println("Enter company");
        employee.setCompany(Company.builder().companyName(scanner.next()).build());
        try {
            employeeService.addEmployee(employee, JWT);
        }catch (Exception e) {
            System.err.println("Failed to add employee: " + e.getMessage());
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
    }

    private void logout() {
        System.out.println("Logged out");
        System.exit(0);
    }
    public void getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
            for (EmployeeDTO employee : employees) {
                System.out.println(employee.toString());
            }
        } catch (Exception e) {
            System.err.println("Failed to get employees: " + e.getMessage());
        }
    }
}
