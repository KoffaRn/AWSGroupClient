package org.koffa.menu;

import org.koffa.model.*;
import org.koffa.service.CityService;
import org.koffa.service.CompanyService;
import org.koffa.service.EmployeeService;
import org.koffa.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AdminMenu {

    // User menu allows the user to control Employees only.
    // The methods for Employees are the public and can be used in the UserMenu class.

    private final String URL = "http://localhost:5000"; //Todo add URL from the properties
    private final String JWT;
    private final String username;
    private final UserService userService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final CityService cityService;

    InputFilter scanner = new InputFilter();

    public AdminMenu(String JWT, String username) {
        this.JWT = JWT;
        this.username = username;
        this.cityService = new CityService(URL);
        this.employeeService = new EmployeeService(URL);
        this.companyService = new CompanyService(URL);
        this.userService = new UserService(URL);
    }


    public void showMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("Welcome " + username + " You are logged in as an admin");
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

    //--------------------------------------------------------------------------------
    private void controlCitys() {
        System.out.println("--------------- Welcome to the City control ---------------");
        System.out.println("1. Add City");
        System.out.println("2. Update City");
        System.out.println("3. Get City by ID");
        System.out.println("4. Delete City");
        System.out.println("5. Get all Citys");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input) {
            case 1:
                addCity();
                controlCitys();
                break;
            case 2:
                updateCity();
                controlCitys();
                break;
            case 3:
                getCityByID();
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

    private void getAllCitys() {

        List<City> cities = cityService.getAllCities(JWT);
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }

    private void deleteCity() {

        // Get the selected city
        City selectedCity = makeUserSelectCity();
        int cityIdToDelete = selectedCity.getCityId();

        // Print the name of the selected city (optional)
        System.out.println("Selected city to delete: " + selectedCity.getCityName());

        // Delete the selected city
        cityService.deleteCity(cityIdToDelete, JWT);
    }

    private void getCityByID() {

        System.out.println("Enter city id: ");
        int id = scanner.nextInt();
        City city = cityService.getCityById(id, JWT);
        System.out.println(city.toString());

    }

    private void updateCity() {

        City city = makeUserSelectCity();

        System.out.println("You chose: " + city.getCityName());
        System.out.println("Enter the new name: ");
        String name = scanner.next();
        CityToSendForUpdate updateCity = new CityToSendForUpdate();
        updateCity.setCityId(city.getCityId());
        updateCity.setCityName(name);

        cityService.updateCity(updateCity, JWT);
    }

    private void addCity() {
        System.out.println("Enter city name");
        String name = scanner.next();

        cityService.addCity(name, JWT);
    }

    //--------------------------------------------------------------------------------


    public void controlEmployees() {
        System.out.println("--------------- Welcome to the Employee control ---------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Get Employee by name");
        System.out.println("4. Delete Employee");
        System.out.println("5. Get all Employees");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input) {
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
            System.out.println(employee.getFirstName());
        }
    }

    public void deleteEmployee() {

        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        System.out.println("Choose employee to delete");

        int i = 1;
        for (EmployeeDTO employee : employees) {
            System.out.println(i++ + ". " + employee.getFirstName() + " " + employee.getLastName());
        }
        int input = scanner.nextInt() - 1;

        EmployeeDTO employee = makeUserSelectEmployee();

        assert employee != null;
        employeeService.deleteEmployee(employee.getId(), JWT);
        System.out.println("Employee: " + employees.get(input).getFirstName() + " deleted.");
    }

    public void getEmployeeByName() {

        System.out.println("Enter employee name: ");
        String name = scanner.next();
        Employee employee = employeeService.getByName(name, JWT);
        System.out.println(employee.toString());

    }

    public void updateEmployee() {

        EmployeeDTO employee = makeUserSelectEmployee();


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
        assert employee != null;
        employeeService.updateEmployee(employee.getId(), updateEmployee, JWT);

    }

    public void addEmployee() {

        City selectedCity = makeUserSelectCity();
        System.out.println("You chose: " + selectedCity.getCityName());

        // Choose a company
        CompanyDTO selectedCompany = makeUserSelectCompany();
        assert selectedCompany != null;
        System.out.println("You chose: " + selectedCompany.getCompanyName());

        Company company = companyService.getCompanyByName(selectedCompany.getCompanyName(), JWT);

        System.out.println("Enter first name: ");
        String firstName = scanner.next();
        System.out.println("Enter last name: ");
        String lastName = scanner.next();
        System.out.println("Enter job title: ");
        String title = scanner.next();
        System.out.println("Enter salary: ");
        double salary = scanner.nextInt();

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setJobTitle(title);
        employee.setSalary(salary);
        employee.setCity(selectedCity);
        employee.setCompany(company);

        employeeService.addEmployee(employee, JWT);
    }

    //--------------------------------------------------------------------------------

    private void controlCompanies() {

        System.out.println("--------------- Welcome to the Company control ---------------");
        System.out.println("1. Add Company");
        System.out.println("2. Update Company");
        System.out.println("3. Get Company by name");
        System.out.println("4. Delete Company");
        System.out.println("5. Get all Company");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input) {
            case 1:
                addCompany();
                controlCompanies();
                break;
            case 2:
                updateCompany();
                controlCompanies();
                break;
            case 3:
                getCompanyByName();
                controlCompanies();
                break;
            case 4:
                deleteCompany();
                controlCompanies();
                break;
            case 5:
                getAllCompanies();
                controlCompanies();
                break;
        }
    }

    private void getAllCompanies() {

        List<CompanyDTO> companies = companyService.getCompanies(JWT);
        for (CompanyDTO company : companies) {
            System.out.println(company.getCompanyName());
        }

    }

    private void deleteCompany() {

        CompanyDTO company = makeUserSelectCompany();
        assert company != null;
        companyService.deleteCompany(company.getCompanyId(), JWT);

        System.out.println("Company deleted.");

    }

    private void getCompanyByName() {

        System.out.println("Enter company name: ");
        String name = scanner.next();
        Company company = companyService.getCompanyByName(name, JWT);
        System.out.println(company.toString());

    }

    private void updateCompany() {

    }

    private void addCompany() {

        System.out.println("Enter company name");
        String name = scanner.next();

        City selectedCity = makeUserSelectCity();
        System.out.println("You chose: " + selectedCity.getCityName());

        CompanyDTO company = new CompanyDTO();
        company.setCompanyName(name);
        company.setCity(selectedCity);
        ArrayList<Employee> employees = new ArrayList<>();
        company.setEmployees(employees);

        System.out.println("Test here");
        companyService.addCompany(company, JWT);

    }


    //--------------------------------------------------------------------------------
    private void controlUsers() {

        System.out.println("--------------- Welcome to the User control ---------------");
        System.out.println("1. Get by ID");
        System.out.println("2. Delete User");
        System.out.println("3. Update User");
        System.out.println("4. Update Role");
        System.out.println("5. Get all Users");
        System.out.println("6. Go back");
        int input = scanner.nextInt();
        if (input == 6) showMenu();

        switch (input) {
            case 1:
                getUserById();
                controlUsers();
                break;
            case 2:
                deleteUser();
                controlUsers();
                break;
            case 3:
                updateUser();
                controlUsers();
                break;
            case 4:
                updateRole();
                controlUsers();
                break;
            case 5:
                getAllUsers();
                controlUsers();
                break;
        }


    }

    private void getAllUsers() {

        List<User> users = userService.getAllUsers(JWT);
        for (User user : users) {
            String role = user.getAuthorities().get(0).getAuthority();
            System.out.println(user.getUserId() + ". " + user.getUsername() + " - " + role);
        }
    }

    private void updateRole() {
        try {
            System.out.println("1. Make admin");
            System.out.println("2. Make user");
            int input = scanner.nextInt();

            User user = makeUserSelectUser();
            System.out.println(user.getUserId());

            int roleId;
            if (input == 1) {
                roleId = 1;
            } else if (input == 2) {
                roleId = 2;
            } else {
                System.out.println("Invalid input");
                return;
            }

            userService.updateUserRole(user.getUserId(), roleId, JWT);
            System.out.println("User role updated successfully." + user.getUsername() + " is now " + (roleId == 1 ? "admin" : "user"));

        } catch (NoSuchElementException e) {
            System.out.println("Invalid input. Please provide a valid option.");
        } catch (RuntimeException e) {
            System.out.println("Failed to update user role: " + e.getMessage());
        }

    }

    private void updateUser() {

        User user = makeUserSelectUser();
        System.out.println("Enter new username: ");
        String newName = scanner.next();
        assert user != null;
        user.setUsername(newName);

        userService.updateUser(user.getUserId(), user, JWT);
        System.out.println("User updated successfully.");

    }

    private void deleteUser() {

        User user = makeUserSelectUser();
        userService.deleteUser((long) user.getUserId(), JWT);
        System.out.println("User deleted successfully.");

    }

    private void getUserById() {
        System.out.println("Enter user ID: ");
        int id = scanner.nextInt();
        User user = userService.getUserById(Long.valueOf(id), JWT);
        System.out.println(user.toString());

    }


    // Code that was repeated in the methods above

    private User makeUserSelectUser() {
        List<User> users = userService.getAllUsers(JWT);
        System.out.println("Choose user to delete");
        int i = 1;
        for (User user : users) {
            System.out.println(i++ + ". " + user.getUsername());
        }
        int input = scanner.nextInt() - 1;
        if (input < 0 || input >= users.size()) {
            System.out.println("Invalid user choice.");
            return null;
        }
        return users.get(input);
    }

    private City makeUserSelectCity() {

        System.out.println("Choose a city:");

        List<City> cities = cityService.getAllCities(JWT);
        int i = 1;
        for (City city : cities) {
            System.out.println(i++ + ". " + city.getCityName());
        }
        int input = scanner.nextInt() - 1;
        if (input < 0 || input >= cities.size()) {
            System.out.println("Invalid city choice.");
            return null;
        }
        return cities.get(input);
    }

    private EmployeeDTO makeUserSelectEmployee() {

        List<EmployeeDTO> employees = employeeService.getEmployees(JWT);
        System.out.println("Choose employee to delete");

        int i = 1;
        for (EmployeeDTO employee : employees) {
            System.out.println(i++ + ". " + employee.getFirstName() + " " + employee.getLastName());
        }
        int input = scanner.nextInt() - 1;
        if (input < 0 || input >= employees.size()) {
            System.out.println("Invalid city choice.");
            return null;
        }
        return employees.get(input);
    }

    private CompanyDTO makeUserSelectCompany() {

        List<CompanyDTO> companies = companyService.getCompanies(JWT);
        System.out.println("Choose company to delete");

        int i = 1;
        for (CompanyDTO company : companies) {
            System.out.println(i++ + ". " + company.getCompanyName());
        }
        int input = scanner.nextInt() - 1;
        if (input < 0 || input >= companies.size()) {
            System.out.println("Invalid city choice.");
            return null;
        }
        return companies.get(input);
    }
}