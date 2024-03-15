package org.koffa;

import org.koffa.menu.UserIdentity;
import org.koffa.model.User;

public class Main {
    public static void main(String[] args) {

        UserIdentity adminMenu = new UserIdentity();

        String jwt = adminMenu.getUserResponse().getJwt();
        System.out.println(jwt);

        User user = adminMenu.getUserResponse().getUser();
        System.out.println(user.authorities.get(0).getAuthority());

    }
}