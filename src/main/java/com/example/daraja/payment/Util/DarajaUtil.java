package com.example.daraja.payment.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DarajaUtil {

    private static final Logger logger = Logger.getLogger(DarajaUtil.class.getName());

    /**
     * Convert Safaricom timestamp format to Date object
     * Format: YYYYMMDDHHmmss
     */
    public static Date parseTransactionDate(String transactionTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            return format.parse(transactionTime);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Failed to parse transaction date: " + transactionTime, e);
            return new Date(); // Return current date as fallback
        }
    }

    /**
     * Format phone number to international format
     * E.g., 0712345678 to 254712345678
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "";
        }

        // Remove any non-digit characters
        String digitsOnly = phoneNumber.replaceAll("\\D", "");

        // Check if the number already has the country code
        if (digitsOnly.startsWith("254")) {
            return digitsOnly;
        }

        // Check if the number starts with 0
        if (digitsOnly.startsWith("0") && digitsOnly.length() >= 10) {
            return "254" + digitsOnly.substring(1);
        }

        // If it doesn't start with 0, assume it's already without country code
        if (digitsOnly.length() >= 9) {
            return "254" + digitsOnly;
        }

        // If we can't format it correctly, return the original digits
        return digitsOnly;
    }

    /**
     * Extract account number from BillRefNumber
     * Handles different formats of account numbers that clients might use
     */
    public static String extractAccountNumber(String billRefNumber) {
        if (billRefNumber == null || billRefNumber.isEmpty()) {
            return "";
        }

        // Remove any spaces, hyphens or special characters
        String cleanRef = billRefNumber.replaceAll("[^a-zA-Z0-9]", "");

        // In case the reference has multiple parts separated by spaces or special chars
        // that are now removed, just take the first part (up to 20 chars)
        if (cleanRef.length() > 20) {
            return cleanRef.substring(0, 20);
        }

        return cleanRef;
    }
}