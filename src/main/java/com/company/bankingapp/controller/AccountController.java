package com.company.bankingapp.controller;

import com.company.bankingapp.model.dto.Account;
import com.company.bankingapp.model.request.CreateAccountRequest;
import com.company.bankingapp.model.request.DebitRequest;
import com.company.bankingapp.model.request.TransferRequest;
import com.company.bankingapp.model.request.WithdrawalRequest;
import com.company.bankingapp.model.response.ApiDataResponse;
import com.company.bankingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<Account>> createAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest) {
        Account account = accountService.createAccount(createAccountRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(account), HttpStatus.CREATED);
    }

    @PostMapping("/debit")
    public ResponseEntity<Void> debit(
            @RequestBody @Valid DebitRequest debitRequest) {
        accountService.debit(debitRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @RequestBody @Valid WithdrawalRequest withdrawalRequest) {
        accountService.withdraw(withdrawalRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> makeTransfer(
            @RequestBody @Valid TransferRequest transferRequest) {
        accountService.makeTransfer(transferRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiDataResponse<Account>> getAccountByAccountNumber(
            @PathVariable @NotNull String accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(new ApiDataResponse<>(account), HttpStatus.OK);
    }
}
