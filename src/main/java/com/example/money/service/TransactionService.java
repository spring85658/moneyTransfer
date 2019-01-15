package com.example.money.service;

import com.example.money.domain.Account;
import com.example.money.domain.Transaction;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.exception.InsufficientFundException;
import com.example.money.repository.AccountRepository;
import com.example.money.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(long fromId, long toId, BigDecimal amount)
            throws AccountNotFoundException, InsufficientFundException {
        Account fromAccount = accountRepository.findById(fromId);
        if (fromAccount == null){
            throw new AccountNotFoundException("From account not found", fromId);
        }
        if (fromAccount.getBalance().compareTo(amount) < 0){
            InsufficientFundException e = new InsufficientFundException("From balance is not enough");
            e.setAccountId(fromId);
            throw e;
        }
        Account toAccount = accountRepository.findById(toId);

        if (toAccount == null){
            throw new AccountNotFoundException("To account not found", toId);
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccountId(fromId);
        transaction.setToAccountId(toId);
        Calendar cal = Calendar.getInstance();
        transaction.setCreateTs(new Timestamp(cal.getTime().getTime()));
        Transaction result = transactionRepository.save(transaction);
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return result;
    }
}
