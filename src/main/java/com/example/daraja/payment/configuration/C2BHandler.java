package com.example.daraja.payment.configuration;

import com.example.daraja.payment.DTO.C2BCallbackRequest;
import com.example.daraja.payment.DTO.C2BCallbackResponse;
import com.example.daraja.payment.Services.DarajaTransactionService;
import com.example.daraja.payment.Services.TransactionLogger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class C2BHandler {

    private static final Logger logger = Logger.getLogger(C2BHandler.class.getName());

    private final DarajaTransactionService transactionService;
    private final TransactionLogger transactionLogger;

    public C2BHandler(DarajaTransactionService transactionService, TransactionLogger transactionLogger) {
        this.transactionService = transactionService;
        this.transactionLogger = transactionLogger;
    }

    public Mono<ServerResponse> handleC2BCallback(ServerRequest request) {
        return request.bodyToMono(C2BCallbackRequest.class)
                .flatMap(req -> {
                    logger.info("Received C2B callback: " + req.toString());

                    boolean success = transactionService.processC2BTransaction(req);

                    C2BCallbackResponse response = new C2BCallbackResponse();
                    if (success) {
                        response.setResultCode("0");
                        response.setResultDesc("Success");
                        transactionLogger.logTransaction(req, "SUCCESS");
                    } else {
                        response.setResultCode("1");
                        response.setResultDesc("Failed");
                        transactionLogger.logTransaction(req, "FAILED");
                    }

                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(error -> {
                    logger.severe("Error processing C2B callback: " + error.getMessage());
                    C2BCallbackResponse errorResponse = new C2BCallbackResponse();
                    errorResponse.setResultCode("1");
                    errorResponse.setResultDesc("Error: " + error.getMessage());
                    return ServerResponse.ok().bodyValue(errorResponse);
                });
    }
}