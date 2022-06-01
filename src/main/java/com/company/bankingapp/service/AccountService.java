package com.company.bankingapp.service;

import com.company.bankingapp.model.dto.Account;
import com.company.bankingapp.model.request.CreateAccountRequest;
import com.company.bankingapp.model.request.DebitRequest;
import com.company.bankingapp.model.request.TransferRequest;
import com.company.bankingapp.model.request.WithdrawalRequest;

public interface AccountService {
    Account getAccountByAccountNumber(String accountNumber);

    Account createAccount(CreateAccountRequest createAccountRequest);

    void withdraw(WithdrawalRequest withdrawRequest);

    void debit(DebitRequest debitRequest);

    void makeTransfer(TransferRequest transferRequest);
}
