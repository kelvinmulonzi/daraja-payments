package com.example.daraja.payment.Services;

import com.example.daraja.payment.DTO.C2BCallbackRequest;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransactionLogger {

    private static final Logger logger = Logger.getLogger(TransactionLogger.class.getName());
    private static final String LOG_DIRECTORY = "logs/transactions";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Log transaction details to a file for audit purposes
     */
    public void logTransaction(C2BCallbackRequest request, String outcome) {
        try {
            // Create directory if it doesn't exist
            Path dirPath = Paths.get(LOG_DIRECTORY);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Create log file with date in filename
            String fileName = LOG_DIRECTORY + "/transactions_" + DATE_FORMAT.format(new Date()) + ".log";

            try (FileWriter fw = new FileWriter(fileName, true);
                 PrintWriter pw = new PrintWriter(fw)) {

                // Write the log entry with timestamp
                pw.println(String.format(
                        "%s|%s|%s|%s|%s|%s|%s|%s",
                        TIMESTAMP_FORMAT.format(new Date()),
                        request.getTransID(),
                        request.getBusinessShortCode(),
                        request.getBillRefNumber(),
                        request.getTransAmount(),
                        request.getPhoneNumber(),
                        request.getTransTime(),
                        outcome
                ));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to log transaction", e);
        }
    }
}
