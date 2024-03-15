package org.koffa.menu;

import lombok.Getter;
import org.koffa.model.LoginResponse;
import org.koffa.model.RegistrationPayload;
import org.koffa.service.*;
import java.util.Scanner;

public class UserIdentity {
    Scanner scan = new Scanner(System.in);
    private AuthService authService;
    @Getter
    private LoginResponse userResponse;

    public UserIdentity () {
        String baseUrl = "http://localhost:5000";
        this.authService = new AuthService(baseUrl);
        start();
    }

    public void start() {
        System.out.println("Welcome");
        System.out.println("1- Login\n2- Register");
        int input = scan.nextInt();

        if (input == 1) {
            System.out.print("Enter username: ");
            String username = scan.next();
            System.out.print("Enter password: ");
            String password = scan.next();
            LoginResponse loginResponse =
                    authService.login(new RegistrationPayload(username, password));

            this.userResponse = loginResponse;
        }
        if (input == 2) {
            System.out.print("Enter username: ");
            String username = scan.next();
            System.out.print("Enter password: ");
            String password = scan.next();
            RegistrationPayload payload = new RegistrationPayload(username, password);
            authService.register(payload);

            System.out.println("User registered successfully\nYour are logged in as " + username);

            LoginResponse loginResponse =
                    authService.login(new RegistrationPayload(username, password));

            this.userResponse = loginResponse;
        }
    }

    public String getUsername() {
        return userResponse.getUser().username;
    }
    public String getToken() {
        return userResponse.getJwt();
    }
    public boolean isAdmin() {
        return userResponse.getUser().authorities.get(0).getAuthority().equals("ADMIN");
    }
}
