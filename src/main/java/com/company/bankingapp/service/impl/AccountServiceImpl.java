package com.company.bankingapp.service.impl;

import com.company.bankingapp.exception.AccountBlockedException;
import com.company.bankingapp.exception.InsufficientBalanceException;
import com.company.bankingapp.exception.ResourceExistsException;
import com.company.bankingapp.exception.ResourceNotFoundException;
import com.company.bankingapp.mapper.AccountMapper;
import com.company.bankingapp.model.dto.Account;
import com.company.bankingapp.model.entity.AccountEntity;
import com.company.bankingapp.model.entity.TransactionEntity;
import com.company.bankingapp.model.enums.AccountStatus;
import com.company.bankingapp.model.enums.TransactionType;
import com.company.bankingapp.model.request.CreateAccountRequest;
import com.company.bankingapp.model.request.DebitRequest;
import com.company.bankingapp.model.request.TransferRequest;
import com.company.bankingapp.model.request.WithdrawalRequest;
import com.company.bankingapp.repository.AccountRepository;
import com.company.bankingapp.service.AccountService;
import com.company.bankingapp.service.CurrencyConversionService;
import com.company.bankingapp.service.CustomerService;
import com.company.bankingapp.service.TransactionService;
import com.company.bankingapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final CurrencyConversionService currencyConversionService;

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        if (existsAccountByNumber(createAccountRequest.getNumber()))
            throw new ResourceExistsException(
                    String.format("Account already exists with number: %s", createAccountRequest.getNumber()));

        var accountEntity = accountMapper.toAccountEntity(createAccountRequest);

        if (createAccountRequest.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            var transactionEntity = transactionService.createTransaction(
                    accountEntity,
                    createAccountRequest.getBalance(),
                    createAccountRequest.getCurrencyType(),
                    TransactionType.DEPOSIT);
            accountEntity.addTransaction(transactionEntity);
        }

        return accountMapper.toAccount(accountRepository.save(accountEntity));
    }

    @Override
    public void withdraw(WithdrawalRequest withdrawRequest) {
        final var username = SecurityUtil.getPrincipal().map(UserDetails::getUsername).get();
        var customer = customerService.findCustomerByUsername(username);
        var account = findCustomerAccountByAccountNumber(customer.getAccountEntities(), withdrawRequest.getAccountNumber());

        if (account.getStatus().equals(AccountStatus.BLOCKED))
            throw new AccountBlockedException("Account already blocked");

        if (account.getBalance().compareTo(withdrawRequest.getAmount()) < 0)
            throw new InsufficientBalanceException("You don't have enough money in your balance");

        final var convertedAmount = currencyConversionService.convert(
                withdrawRequest.getCurrencyType(),
                account.getCurrencyType(),
                withdrawRequest.getAmount());

        var transaction = transactionService.createTransaction(
                account, convertedAmount,
                withdrawRequest.getCurrencyType(),
                TransactionType.WITHDRAW);

        account.addTransaction(transaction);
        account.setBalance(account.getBalance().subtract(convertedAmount));
        accountRepository.save(account);
    }

    private AccountEntity findCustomerAccountByAccountNumber(Set<AccountEntity> accountEntities, String accountNumber) {
        return accountEntities.stream()
                .filter(accountEntity -> accountEntity.getNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account not found with number: %s", accountNumber)));

    }

    private AccountEntity findAccountByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account not found with number: %s", accountNumber)));
    }

    @Override
    public void debit(DebitRequest debitRequest) {
        final var username = SecurityUtil.getPrincipal().map(UserDetails::getUsername).get();
        var customer = customerService.findCustomerByUsername(username);
        var account = findCustomerAccountByAccountNumber(customer.getAccountEntities(), debitRequest.getAccountNumber());

        if (account.getStatus().equals(AccountStatus.BLOCKED))
            throw new AccountBlockedException("Account already blocked");

        final var convertedAmount = currencyConversionService.convert(
                debitRequest.getCurrencyType(),
                account.getCurrencyType(),
                debitRequest.getAmount());

        var transaction = transactionService.createTransaction(
                account, convertedAmount,
                debitRequest.getCurrencyType(),
                TransactionType.DEPOSIT);

        account.addTransaction(transaction);
        account.setBalance(account.getBalance().add(convertedAmount));
        accountRepository.save(account);
    }

    @Override
    public void makeTransfer(TransferRequest transferRequest) {
        var fromAccount = findAccountByNumber(transferRequest.getFromAccountNumber());
        var toAccount = findAccountByNumber(transferRequest.getToAccountNumber());
        var amount = currencyConversionService.convert(
                fromAccount.getCurrencyType(),
                toAccount.getCurrencyType(),
                transferRequest.getAmount());

        var fromAccountTransaction = transactionService.createTransaction(
                fromAccount, amount,
                fromAccount.getCurrencyType(),
                TransactionType.WITHDRAW);

        var toAccountTransaction = transactionService.createTransaction(
                toAccount, amount,
                toAccount.getCurrencyType(),
                TransactionType.DEPOSIT);

        fromAccount.addTransaction(fromAccountTransaction);

        accountRepository.save(fromAccount);
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequest.getAmount()));

        toAccount.addTransaction(toAccountTransaction);
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);
    }

    private boolean existsAccountByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }
}
