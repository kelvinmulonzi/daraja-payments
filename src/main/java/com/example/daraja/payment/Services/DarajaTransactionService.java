package com.example.daraja.payment.Services;

//import com.example.daraja.payment.DTO.C2BCallbackRequest;
import com.example.daraja.payment.DTO.C2BCallbackRequest;
import com.example.daraja.payment.Entities.PaymentDetail;
import com.example.daraja.payment.Repos.LoanTransactionRepository;
import com.example.daraja.payment.Repos.PaymentDetailRepository;
import com.example.daraja.payment.Repos.SavingsTransactionRepository;
import com.example.daraja.payment.Repos.ShareTransactionRepository;
import com.example.daraja.payment.Util.DarajaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DarajaTransactionService {

    private static final Logger logger = Logger.getLogger(DarajaTransactionService.class.getName());

    private final LoanTransactionRepository loanTransactionRepository;
    private final SavingsTransactionRepository savingsTransactionRepository;
    private final ShareTransactionRepository shareTransactionRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final AccountService accountService;
    private final TenantResolverService tenantResolverService;

    @Autowired
    public DarajaTransactionService(
            LoanTransactionRepository loanTransactionRepository,
            SavingsTransactionRepository savingsTransactionRepository,
            ShareTransactionRepository shareTransactionRepository,
            PaymentDetailRepository paymentDetailRepository,
            AccountService accountService,
            TenantResolverService tenantResolverService) {
        this.loanTransactionRepository = loanTransactionRepository;
        this.savingsTransactionRepository = savingsTransactionRepository;
        this.shareTransactionRepository = shareTransactionRepository;
        this.paymentDetailRepository = paymentDetailRepository;
        this.accountService = accountService;
        this.tenantResolverService = tenantResolverService;
    }

    @Transactional
    public boolean processC2BTransaction(C2BCallbackRequest request) {
        try {
            logger.info("Processing C2B transaction: " + request.getTransID());

            // Set tenant context based on business shortcode
            Long tenantId = tenantResolverService.resolveTenantFromShortCode(request.getBusinessShortCode());
            logger.info("Resolved tenant ID: " + tenantId + " for shortcode: " + request.getBusinessShortCode());

            // Extract and clean account number from BillRefNumber
            String accountNumber = DarajaUtil.extractAccountNumber(request.getBillRefNumber());

            if (accountNumber.isEmpty()) {
                logger.warning("Invalid account number from BillRefNumber: " + request.getBillRefNumber());
                return false;
            }

            // Save payment detail first
            PaymentDetail paymentDetail = savePaymentDetail(request, tenantId);

            // Determine account type and save transaction
            String accountType = accountService.determineAccountType(accountNumber);

            BigDecimal amount = new BigDecimal(request.getTransAmount());

            switch (accountType) {
                case "LOAN":
                    logger.info("Processing LOAN transaction for account: " + accountNumber);
                    saveLoanTransaction(accountNumber, amount, paymentDetail);
                    break;
                case "SAVINGS":
                    logger.info("Processing SAVINGS transaction for account: " + accountNumber);
                    saveSavingsTransaction(accountNumber, amount, paymentDetail);
                    break;
                case "SHARE":
                    logger.info("Processing SHARE transaction for account: " + accountNumber);
                    saveShareTransaction(accountNumber, amount, paymentDetail);
                    break;
                default:
                    logger.warning("Unknown account type for account: " + accountNumber);
                    return false;
            }

            logger.info("Successfully processed C2B transaction for account: " + accountNumber);
            return true;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing C2B transaction", e);
            return false;
        }
    }

    private PaymentDetail savePaymentDetail(C2BCallbackRequest request, Long tenantId) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setReceiptNumber(request.getTransID());
        paymentDetail.setPaymentType("M-PESA");
        paymentDetail.setAmount(new BigDecimal(request.getTransAmount()));
        paymentDetail.setPaymentDate(request.getTransTime());
        paymentDetail.setPhoneNumber(DarajaUtil.formatPhoneNumber(request.getPhoneNumber()));
        paymentDetail.setTenantId(tenantId);

        return paymentDetailRepository.save(paymentDetail);
    }

    private void saveLoanTransaction(String accountNumber, BigDecimal amount, PaymentDetail paymentDetail) {
        loanTransactionRepository.saveC2BTransaction(accountNumber, amount, paymentDetail.getId());
    }

    private void saveSavingsTransaction(String accountNumber, BigDecimal amount, PaymentDetail paymentDetail) {
        savingsTransactionRepository.saveC2BTransaction(accountNumber, amount, paymentDetail.getId());
    }

    private void saveShareTransaction(String accountNumber, BigDecimal amount, PaymentDetail paymentDetail) {
        shareTransactionRepository.saveC2BTransaction(accountNumber, amount, paymentDetail.getId());
    }
}