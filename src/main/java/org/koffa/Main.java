package org.koffa;

import org.koffa.menu.AdminMenu;
import org.koffa.menu.UserIdentity;
import org.koffa.model.User;

public class Main {
    public static void main(String[] args) {

        UserIdentity auth = new UserIdentity();

        AdminMenu adminMenu1 = new AdminMenu(auth.getToken(), auth.getUsername());

        System.out.println(auth.isAdmin());

        adminMenu1.showMenu();

    }
}