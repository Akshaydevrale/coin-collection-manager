package com.phoenix;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        CoinOperations co = new CoinOperations();
        Scanner sc = new Scanner(System.in);

        co.addDatabase(); // Load from DB on startup

        while (true) {
            System.out.println("\n--- Coin Collection Menu ---");
            System.out.println("1. Add New Coin");
            System.out.println("2. Bulk Upload from File");
            System.out.println("3. Display All Coins");
            System.out.println("4. Search Coins");
            System.out.println("5. Save to Database");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: co.addCoinManually(sc); break;
                case 2: 
                    System.out.print("Enter file name: ");
                    sc.nextLine();
                    String file = sc.nextLine();
                    co.bulkUploadFromFile(file);
                    break;
                case 3: co.displayAllCoinsDetails(); break;
                case 4: co.searchCoins(sc); break;
                case 5: co.saveToDatabase(); break;
                case 6: 
                    System.out.println("Saving data before exit...");
                    co.saveToDatabase();
                    System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
