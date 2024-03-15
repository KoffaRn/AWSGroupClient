package org.koffa;

import org.koffa.menu.UserIdentity;
import org.koffa.model.User;

public class Main {
    public static void main(String[] args) {

        UserIdentity adminMenu = new UserIdentity();


        System.out.println(adminMenu.getUsername());
        System.out.println(adminMenu.getToken());
        System.out.println(adminMenu.isAdmin());



    }
}