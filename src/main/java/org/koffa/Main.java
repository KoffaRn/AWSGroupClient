package org.koffa;

import org.koffa.menu.AdminMenu;
import org.koffa.menu.UserIdentity;
import org.koffa.menu.UserMenu;

public class Main {
    public static void main(String[] args) {

        UserIdentity auth = new UserIdentity();

        if (auth.isAdmin()) {
            AdminMenu adminMenu1 = new AdminMenu(auth.getToken(), auth.getUsername());
            adminMenu1.showMenu();
        } else {
            UserMenu userMenu1 = new UserMenu(auth.getToken(), auth.getUsername());
            userMenu1.showMenu();
        }
    }
}