package com.mirrors;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MainController mainController = MainController.getInstance();
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("Enter filename here:");
            String fileName = input.nextLine();
            mainController.parseFile(fileName);
            mainController.generateResult();
            mainController.printResult();
        }
    }
}
