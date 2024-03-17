package org.koffa.menu;

import java.util.Scanner;

public class InputFilter {


    Scanner scanner = new Scanner(System.in);


    public int nextInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                return input;
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public double nextDouble() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double input = scanner.nextDouble();
                return input;
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public String next() {
        while (true) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                return input;
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a valid string.");
            }
        }
    }


}
