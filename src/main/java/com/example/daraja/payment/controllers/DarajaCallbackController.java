//package com.example.daraja.payment.controllers;
//
////import com.example.daraja.payment.DTO.C2BCallbackRequest;
//import com.example.daraja.payment.DTO.C2BCallbackResponse;
//import com.example.daraja.payment.Services.DarajaTransactionService;
//import com.example.daraja.payment.Services.TransactionLogger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/api/daraja")
//public class DarajaCallbackController {
//
//    private static final Logger logger = Logger.getLogger(DarajaCallbackController.class.getName());
//
//    private final DarajaTransactionService transactionService;
//    private final TransactionLogger transactionLogger;
//
//    @Autowired
//    public DarajaCallbackController(DarajaTransactionService transactionService, TransactionLogger transactionLogger) {
//        this.transactionService = transactionService;
//        this.transactionLogger = transactionLogger;
//    }
//
//    @PostMapping("/c2b/callback")
//    public ResponseEntity<C2BCallbackResponse> handleC2BCallback(@Valid @RequestBody C2BCallbackRequest request) {
//        logger.info("Received C2B callback: " + request.toString());
//
//        // Process the transaction and map to appropriate table
//        boolean success = transactionService.processC2BTransaction(request);
//
//        // Prepare response as per Daraja requirements
//        C2BCallbackResponse response = new C2BCallbackResponse();
//        if (success) {
//            response.setResultCode("0");
//            response.setResultDesc("Success");
//            transactionLogger.logTransaction(request, "SUCCESS");
//        } else {
//            response.setResultCode("1");
//            response.setResultDesc("Failed");
//            transactionLogger.logTransaction(request, "FAILED");
//        }
//
//        return ResponseEntity.ok(response);
//    }}