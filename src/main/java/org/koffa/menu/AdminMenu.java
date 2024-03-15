package org.koffa.menu;

import org.koffa.model.*;
import org.koffa.service.CityService;
import org.koffa.service.CompanyService;
import org.koffa.service.EmployeeService;
import org.koffa.service.UserService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final String URL = null; //Todo add URL from the properties
    private final String JWT;
    private final String username;
    Scanner scanner = new Scanner(System.in);

    private UserService userService;
    private CompanyService companyService;
    private EmployeeService employeeService;
    private CityService cityService;
    
    public AdminMenu (String JWT, String username) {
        this.JWT = JWT;
        this.username = username;
        this.cityService = new CityService(URL);
        this.employeeService = new EmployeeService(URL);
        this.companyService = new CompanyService(URL);
        this.userService = new UserService(URL);
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
        System.out.println("--------------- Welcome to the City control ---------------");
        System.out.println("1. Add City");
        System.out.println("2. Update City");
        System.out.println("3. Get City by name");
        System.out.println("4. Delete City");
        System.out.println("5. Get all Citys");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input){
            case 1:
                addCity();
                controlCitys();
                break;
            case 2:
                updateCity();
                controlCitys();
                break;
            case 3:
                getCityByName();
                controlCitys();
                break;
            case 4:
                deleteCity();
                controlCitys();
                break;
            case 5:
                getAllCitys();
                controlCitys();
                break;
        }
    }




    // OK to use this for the user menu as well
    public void getAllCitys() {

        List<City> cities = cityService.getAllCities(JWT);
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }
    public void deleteCity() {

        List<City> cities = cityService.getAllCities(JWT);
        System.out.println("Choose city to delete");

        for (City city : cities) {
            System.out.println(city.getId() + ". " + city.getCityName());
        }
        int input = scanner.nextInt();
        cityService.deleteCity(cities.get(input).getId(), JWT);
    }
    public void getCityByName() {

        System.out.println("Enter city name: ");
        String name = scanner.next();
        City city = cityService.getCityByName(name, JWT);
        System.out.println(city.toString());

    }
    public void updateCity() {
        List<City> cities = cityService.getAllCities(JWT);
        System.out.println("Choose city to update");

        for (City city : cities) {
            System.out.println(city.getId() + ". " + city.getCityName());
        }
        int input = scanner.nextInt();
        System.out.println("Enter the new name: ");
        String name = scanner.next();
        City updateCity = new City();
        updateCity.setCityName(name);

        cityService.updateCity(cities.get(input).getId(), updateCity, JWT);
    }
    public void addCity() {
        System.out.println("Enter city name");
        String name = scanner.next();
        City city = new City();
        city.setCityName(name);
        city.setEmployees(new ArrayList<>());
        cityService.addCity(city, JWT);
    }

    //--------------------------------------------------------------------------------



    private void controlEmployees() {
        System.out.println("--------------- Welcome to the Employee control ---------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Get Employee by name");
        System.out.println("4. Delete Employee");
        System.out.println("5. Get all Employees");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input){
            case 1:
                addEmployee();
                controlEmployees();
                break;
            case 2:
                updateEmployee();
                controlEmployees();
                break;
            case 3:
                getEmployeeByName();
                controlEmployees();
                break;
            case 4:
                deleteEmployee();
                controlEmployees();
                break;
            case 5:
                getAllEmployees();
                controlEmployees();
                break;
        }



    }

    // OK to use this for the user menu as well

    public void getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        for (EmployeeDTO employee : employees) {
            System.out.println(employee.toString());
        }
    }
    public void deleteEmployee() {

        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        System.out.println("Choose employee to delete");

        for (EmployeeDTO employee : employees) {
            System.out.println(employee.getId() + ". " + employee.getFirstName() + " " + employee.getLastName());
        }
        int input = scanner.nextInt();
        employeeService.deleteEmployee(employees.get(input).getId(), JWT);

    }
    public void getEmployeeByName() {

        System.out.println("Enter employee name: ");
        String name = scanner.next();
        Employee employee = employeeService.getByName(name, JWT);
        System.out.println(employee.toString());

    }
    public void updateEmployee() {

        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        System.out.println("Choose employee to update");

        for (EmployeeDTO employee : employees) {
            System.out.println(employee.getId() + ". " + employee.getFirstName() + " " + employee.getLastName());
        }

        int input = scanner.nextInt();
        System.out.println("Enter the new first name: ");
        String firstName = scanner.next();
        System.out.println("Enter the new last name: ");
        String lastName = scanner.next();
        System.out.println("Enter the new job title: ");
        String title = scanner.next();
        System.out.println("Enter the new salary: ");
        int salary = scanner.nextInt();
        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName(firstName);
        updateEmployee.setLastName(lastName);
        updateEmployee.setJobTitle(title);
        updateEmployee.setSalary(salary);
        employeeService.updateEmployee(employees.get(input).getId(), updateEmployee, JWT);

    }
    public void addEmployee() {

        System.out.println("Chose a city");
        List<City> cities = cityService.getAllCities(JWT);
        for (City city : cities) {
            System.out.println(city.getId() + ". " + city.getCityName());
        }
        int input = scanner.nextInt();
        City city = cities.get(input);

        System.out.println("Type the name of company");
        List<CompanyDTO> companies = companyService.getCompanies(JWT);
        for (CompanyDTO company : companies) {
            System.out.println(company.getCompanyName());
        }
        String companyInput = scanner.next();
        CompanyDTO company = companyService.getCompanyByName(companyInput, JWT);
        Company company1 = new Company();
        company1.setCompanyName(company.getCompanyName());
        company1.setCity(city);
        company1.setEmployees(new ArrayList<>());

        System.out.println("Enter first name: ");
        String firstName = scanner.next();
        System.out.println("Enter last name: ");
        String lastName = scanner.next();
        System.out.println("Enter job title: ");
        String title = scanner.next();
        System.out.println("Enter salary: ");
        int salary = scanner.nextInt();

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setJobTitle(title);
        employee.setSalary(salary);
        employee.setCity(city);
        employee.setCompany(company1);

        employeeService.addEmployee(employee, JWT);
    }
    //--------------------------------------------------------------------------------

    private void controlCompanies() {

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
