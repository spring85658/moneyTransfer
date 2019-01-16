package com.example.money.service;

import com.example.money.domain.Account;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    public BigDecimal getAccountBalanceById(long id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id);
        if (account != null){
            logger.info("Account {} balance {} ",id, account.getBalance());
            return account.getBalance();
        }else{
            logger.error("Account id {} not found", id);
            throw new AccountNotFoundException("Account Not Found", id);
        }
    }
}
