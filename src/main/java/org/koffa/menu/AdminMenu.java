package org.koffa.menu;

import org.koffa.model.City;
import org.koffa.model.Employee;
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

    }

    public void deleteEmployee() {

    }

    public void getEmployeeByName() {

    }

    public void updateEmployee() {

    }

    public void addEmployee() {

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
