package com.example.daraja.payment.Services;

import com.example.daraja.payment.Repos.LoanTransactionRepository;
import com.example.daraja.payment.Repos.SavingsTransactionRepository;
import com.example.daraja.payment.Repos.ShareTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    private final LoanTransactionRepository loanTransactionRepository;
    private final SavingsTransactionRepository savingsTransactionRepository;
    private final ShareTransactionRepository shareTransactionRepository;

    @Autowired
    public AccountService(
            LoanTransactionRepository loanTransactionRepository,
            SavingsTransactionRepository savingsTransactionRepository,
            ShareTransactionRepository shareTransactionRepository) {
        this.loanTransactionRepository = loanTransactionRepository;
        this.savingsTransactionRepository = savingsTransactionRepository;
        this.shareTransactionRepository = shareTransactionRepository;
    }

    /**
     * Determines the type of account based on the account number
     *
     * @param accountNumber the account number to check
     * @return the account type (LOAN, SAVINGS, SHARE) or UNKNOWN
     */
    public String determineAccountType(String accountNumber) {
        logger.info("Determining account type for: " + accountNumber);

        // Check account type based on prefix or database lookup
        if (accountNumber.startsWith("L")) {
            // Account number starts with L, assume it's a loan account
            return "LOAN";
        } else if (accountNumber.startsWith("S")) {
            // Account number starts with S, assume it's a savings account
            return "SAVINGS";
        } else if (accountNumber.startsWith("SH")) {
            // Account number starts with SH, assume it's a share account
            return "SHARE";
        } else {
            // If no prefix match, check in databases
            if (loanTransactionRepository.accountExists(accountNumber)) {
                return "LOAN";
            } else if (savingsTransactionRepository.accountExists(accountNumber)) {
                return "SAVINGS";
            } else if (shareTransactionRepository.accountExists(accountNumber)) {
                return "SHARE";
            } else {
                return "UNKNOWN";
            }
        }
    }
}
