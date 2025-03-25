package com.example.daraja.payment.configuration;

import com.example.daraja.payment.DTO.C2BCallbackRequest;
import com.example.daraja.payment.DTO.C2BCallbackResponse;
import com.example.daraja.payment.Services.DarajaTransactionService;
import com.example.daraja.payment.Services.TransactionLogger;
import com.example.daraja.payment.db.MariaDBConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.util.logging.Logger;

@Component
public class C2BHandler {

    MariaDBConnect mariaDBConnect;
    private static final Logger logger = Logger.getLogger(C2BHandler.class.getName());

    private final DarajaTransactionService transactionService;
    private final TransactionLogger transactionLogger;

    public C2BHandler(DarajaTransactionService transactionService, TransactionLogger transactionLogger) {
        this.transactionService = transactionService;
        this.transactionLogger = transactionLogger;
    }

    public Mono<ServerResponse> handleC2BCallback(ServerRequest request) {
        Gson gson = new Gson();

        return request.bodyToMono(String.class)
                .doOnNext(body -> System.out.println("Raw request body: " + body)) // Print raw request body
                .flatMap(body -> {
                    try {
                        // Convert JSON string to JsonObject
                        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);

                        // Extract data step by step
                        JsonObject stkCallback = jsonObject
                                .getAsJsonObject("Body")
                                .getAsJsonObject("stkCallback");

                        String merchantRequestID = stkCallback.get("MerchantRequestID").getAsString();
                        String checkoutRequestID = stkCallback.get("CheckoutRequestID").getAsString();
                        int resultCode = stkCallback.get("ResultCode").getAsInt();
                        String resultDesc = stkCallback.get("ResultDesc").getAsString();

                        JsonObject callbackMetadata = stkCallback.getAsJsonObject("CallbackMetadata");

                        String amount = "";
                        String mpesaReceiptNumber = "";
                        String transactionDate = "";
                        String phoneNumber = "";

                        // Extracting callback metadata items
                        for (JsonElement item : callbackMetadata.getAsJsonArray("Item")) {
                            JsonObject obj = item.getAsJsonObject();
                            String name = obj.get("Name").getAsString();
                            if (name.equals("Amount")) {
                                amount = obj.get("Value").getAsString();
                            } else if (name.equals("MpesaReceiptNumber")) {
                                mpesaReceiptNumber = obj.get("Value").getAsString();
                            } else if (name.equals("TransactionDate")) {
                                transactionDate = obj.get("Value").getAsString();
                            } else if (name.equals("PhoneNumber")) {
                                phoneNumber = obj.get("Value").getAsString();
                            }
                        }

                        // Print extracted values
                        System.out.println("MerchantRequestID: " + merchantRequestID);
                        System.out.println("CheckoutRequestID: " + checkoutRequestID);
                        System.out.println("ResultCode: " + resultCode);
                        System.out.println("ResultDesc: " + resultDesc);
                        System.out.println("Amount: " + amount);
                        System.out.println("MpesaReceiptNumber: " + mpesaReceiptNumber);
                        System.out.println("TransactionDate: " + transactionDate);
                        System.out.println("PhoneNumber: " + phoneNumber);
                        String result = MariaDBConnect.insert_mloans(Double.valueOf(amount), mpesaReceiptNumber, phoneNumber);
                        // Create a response object
                        C2BCallbackResponse response = new C2BCallbackResponse();
                        if (resultCode == 0) {
                            response.setResultCode("0");
                            response.setResultDesc("Success");
                            //transactionLogger.logTransaction(body, "SUCCESS");
                        } else {
                            response.setResultCode("1");
                            response.setResultDesc("Failed");
                            //transactionLogger.logTransaction(body, "FAILED");
                        }

                        return ServerResponse.ok().bodyValue(response);
                    } catch (Exception e) {
                        System.out.println("Error parsing JSON: " + e.getMessage());
                        return ServerResponse.badRequest().bodyValue("Invalid JSON format");
                    }
                })
                .onErrorResume(error -> {
                    System.out.println("Error processing C2B callback: " + error.getMessage());
                    C2BCallbackResponse errorResponse = new C2BCallbackResponse();
                    errorResponse.setResultCode("1");
                    errorResponse.setResultDesc("Error: " + error.getMessage());
                    return ServerResponse.ok().bodyValue(errorResponse);
                });
    }

}