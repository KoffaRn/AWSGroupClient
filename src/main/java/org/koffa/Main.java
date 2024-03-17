package org.koffa;

import org.koffa.menu.AdminMenu;
import org.koffa.menu.UserIdentity;

public class Main {
    public static void main(String[] args) {

        UserIdentity auth = new UserIdentity();

        if (auth.isAdmin()) {
            AdminMenu adminMenu1 = new AdminMenu(auth.getToken(), auth.getUsername());
            adminMenu1.showMenu();
        } else {
            AdminMenu adminMenu2 = new AdminMenu(auth.getToken(), auth.getUsername());
            adminMenu2.controlEmployees();
        }
    }
}