package org.koffa.menu;

import java.util.Scanner;

public class AdminMenu {
    private final String JWT;
    private final String username;
    Scanner scanner = new Scanner(System.in);
    
    public AdminMenu (String JWT, String username) {
        this.JWT = JWT;
        this.username = username;
        
    }


    public void showMenu() {
        System.out.println("Welcome " + username + "You are logged in as an admin");
        System.out.println("1. Control Users");
        System.out.println("2. Control Companies");
        System.out.println("3. Control Employees");
        System.out.println("4. Control Citys");
        System.out.println("5. Logout");
        System.out.println("6. Go back: ");
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
            case 6:
                showMenu();
                break;
        }
        
        
        
        
    }

    private void logout() {
    }

    private void controlCitys() {
    }

    private void controlEmployees() {
    }

    private void controlCompanies() {
    }

    private void controlUsers() {
    }


}
