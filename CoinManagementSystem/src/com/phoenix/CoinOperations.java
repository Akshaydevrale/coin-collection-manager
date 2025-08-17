package com.phoenix;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class CoinOperations {

    List<Coin> coins = new ArrayList<>();

    public void addDatabase() {
        try (Connection con = DBUtility.getSQLConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM cointable")) {

            while (res.next()) {
                Coin c = new Coin(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3),
                        res.getInt(4),
                        res.getDouble(5),
                        res.getDate(6).toLocalDate()
                );
                coins.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAllCoinsDetails() {
        if (coins.isEmpty()) {
            System.out.println("No coins available.");
        } else {
            coins.forEach(System.out::println);
        }
    }

    public void addCoinManually(Scanner sc) {
        try {
            System.out.print("Enter Coin ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Country: ");
            String country = sc.nextLine();

            System.out.print("Denomination: ");
            int denomination = sc.nextInt();

            System.out.print("Year of Minting: ");
            int year = sc.nextInt();

            System.out.print("Current Value: ");
            double value = sc.nextDouble();
            sc.nextLine();

            LocalDate date;
            while (true) {
                try {
                    System.out.print("Acquired Date (yyyy-mm-dd): ");
                    String dateStr = sc.nextLine();
                    date = LocalDate.parse(dateStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format! Please enter as yyyy-mm-dd.");
                }
            }

            Coin c = new Coin(id, country, denomination, year, value, date);
            coins.add(c);
            System.out.println("Coin added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding coin. Please try again.");
            sc.nextLine();
        }
    }

    public void bulkUploadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Coin c = new Coin(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4]),
                        LocalDate.parse(parts[5])
                );
                coins.add(c);
            }
            System.out.println("Bulk upload complete.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO cointable (coinId, country, denomination, yearOfMinting, currentValue, acquiredDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtility.getSQLConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM cointable");
            }

            for (Coin c : coins) {
                pstmt.setInt(1, c.getCoinId());
                pstmt.setString(2, c.getCountry());
                pstmt.setInt(3, c.getDenomination());
                pstmt.setInt(4, c.getYearOfMinting());
                pstmt.setDouble(5, c.getCurrentValue());
                pstmt.setDate(6, java.sql.Date.valueOf(c.getAquireDate()));

                pstmt.executeUpdate();
            }

            System.out.println("All coins saved to the database successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void searchCoins(Scanner sc) {
        System.out.println("\nSearch by:");
        System.out.println("1. Country");
        System.out.println("2. Year of Minting");
        System.out.println("3. Current Value (sorted)");
        System.out.println("4. Country + Denomination");
        System.out.println("5. Country + Year of Minting");
        System.out.println("6. Country + Denomination + Year of Minting");
        System.out.println("7. Acquired Date + Country");
        int ch = sc.nextInt();
        sc.nextLine();

        switch (ch) {
            case 1:
                System.out.print("Enter country: ");
                String country = sc.nextLine();
                coins.stream()
                        .filter(c -> c.getCountry().equalsIgnoreCase(country))
                        .forEach(System.out::println);
                break;
            case 2:
                System.out.print("Enter year: ");
                int year = sc.nextInt();
                coins.stream()
                        .filter(c -> c.getYearOfMinting() == year)
                        .forEach(System.out::println);
                break;
            case 3:
                coins.stream()
                        .sorted(Comparator.comparingDouble(Coin::getCurrentValue))
                        .forEach(System.out::println);
                break;
            case 4:
                System.out.print("Country: ");
                country = sc.nextLine();
                System.out.print("Denomination: ");
                int denom = sc.nextInt();
                coins.stream()
                        .filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getDenomination() == denom)
                        .forEach(System.out::println);
                break;
            case 5:
                System.out.print("Country: ");
                country = sc.nextLine();
                System.out.print("Year: ");
                year = sc.nextInt();
                coins.stream()
                        .filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getYearOfMinting() == year)
                        .forEach(System.out::println);
                break;
            case 6:
                System.out.print("Country: ");
                country = sc.nextLine();
                System.out.print("Denomination: ");
                denom = sc.nextInt();
                System.out.print("Year: ");
                year = sc.nextInt();
                coins.stream()
                        .filter(c -> c.getCountry().equalsIgnoreCase(country) &&
                                c.getDenomination() == denom &&
                                c.getYearOfMinting() == year)
                        .forEach(System.out::println);
                break;
            case 7:
                System.out.print("Date (yyyy-mm-dd): ");
                LocalDate date = LocalDate.parse(sc.nextLine());
                System.out.print("Country: ");
                country = sc.nextLine();
                coins.stream()
                        .filter(c -> c.getAquireDate().equals(date) && c.getCountry().equalsIgnoreCase(country))
                        .forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
