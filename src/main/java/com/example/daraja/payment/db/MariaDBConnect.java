package com.example.daraja.payment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MariaDBConnect {
    public static String url = "jdbc:mariadb://localhost:3306/daraja_payments"; // Change DB name if different
    public static String user = "";  // Change if you have a different username
    public static String password = "mutuku";  // Change to your actual password

    public static String insert_mloans(Double amount, String mpesa_receipt_number, String phone_number) {
        // Database connection details

        // SQL Insert Query
        String insertSQL = "INSERT INTO payments (amount, mpesa_receipt_number, phone_number) VALUES (?, ?, ?)";

        try {
            // Connect to MariaDB
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MariaDB!");

            // Prepare the SQL statement
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setDouble(1, amount); // Amount
            pstmt.setString(2, mpesa_receipt_number); // Mpesa Receipt Number
            pstmt.setString(3, phone_number); // Phone Number

            // Execute insert
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
                return  "Data inserted successfully!";
            }

            // Close connection
            pstmt.close();
            conn.close();
            return  "Failed to insert data";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error inserting data!"+e.getMessage();
        }
    }
}
