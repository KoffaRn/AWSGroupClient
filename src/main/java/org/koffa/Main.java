package org.koffa;

import org.koffa.model.City;
import org.koffa.service.CityService;

public class Main {
    public static void main(String[] args) {
        CityService cityService = new CityService("http://localhost:5000");

        //admin token for testing
        String jwt = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MTA0MTg3NTcsInJvbGVzIjoiQURNSU4ifQ.t8Uih0hVXll8XsTYz00pU1OidOuHXLjuaNlnlYjkiY4me19nYrS4qCk1Kg8_1bpJNBIg1i7UlC36OaiR-LtPR_1Fj-I6Po8_YtrsypkQDtPXCQoHhwaOWTs4HMJwXDP10DLgye-udBLU2Wqto4r0dZ10gzQwrwOSnNR2jvVS4sY9x56L2aCPZwRrVkkpyCDrZ820gGsmxmKJnpc7_uZQwg9eU3Y-9gWXV9QmEuibj9Qx2GRcqxb8KG7lqLAmBaDc6V8L7bKVlrLs4JeD0zb15jUYkLQGDrYXhUnJOjsXvZjBmoWvEMyltxuwOUii9yhZW5_XkaTXOHfLM5DwyckPVA";
        System.out.println("-------------------");

        /*

        City city = cityService.getCityById(4L, jwt);
        System.out.println(city);
        System.out.println("-------------------");


*/
        cityService.getAllCities(jwt).forEach(System.out::println);
        System.out.println("-------------------");

        /*


        System.out.println(cityService.getCityById(4L, jwt));
        System.out.println("-------------------");


         */

        //System.out.println(cityService.deleteCity(4L, jwt));


    }
}