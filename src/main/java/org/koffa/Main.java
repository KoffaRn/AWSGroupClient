package org.koffa;

import org.koffa.service.CityService;
import org.koffa.service.UserService;

public class Main {
    public static void main(String[] args) {
        String jwt = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MTA0MTg3NTcsInJvbGVzIjoiQURNSU4ifQ.t8Uih0hVXll8XsTYz00pU1OidOuHXLjuaNlnlYjkiY4me19nYrS4qCk1Kg8_1bpJNBIg1i7UlC36OaiR-LtPR_1Fj-I6Po8_YtrsypkQDtPXCQoHhwaOWTs4HMJwXDP10DLgye-udBLU2Wqto4r0dZ10gzQwrwOSnNR2jvVS4sY9x56L2aCPZwRrVkkpyCDrZ820gGsmxmKJnpc7_uZQwg9eU3Y-9gWXV9QmEuibj9Qx2GRcqxb8KG7lqLAmBaDc6V8L7bKVlrLs4JeD0zb15jUYkLQGDrYXhUnJOjsXvZjBmoWvEMyltxuwOUii9yhZW5_XkaTXOHfLM5DwyckPVA";


        CityService cityService = new CityService("http://localhost:5000");


        UserService userService = new UserService("http://localhost:5000");


        // Get all users
        System.out.println("All Users:");
        System.out.println(userService.getAllUsers(jwt));
        System.out.println("-------------------");

// Get user by ID
        System.out.println("User by ID:");
        System.out.println(userService.getUserById(2L, jwt));
        System.out.println("-------------------");

// Update user
        System.out.println("Update User:");
        String updatedUser = "{\"username\": \"newUsername\", \"password\": \"newPassword\"}";
        System.out.println(userService.updateUser(1L, updatedUser, jwt));
        System.out.println("-------------------");

// Update user role
        System.out.println("Update User Role:");
        System.out.println(userService.updateUserRole(1L, 2, jwt));
        System.out.println("-------------------");

// Delete user
        System.out.println("Delete User:");
        System.out.println(userService.deleteUser(2L, jwt));
        System.out.println("-------------------");


    }
}