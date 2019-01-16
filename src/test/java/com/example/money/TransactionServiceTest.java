package com.example.money;

import com.example.money.domain.Account;
import com.example.money.domain.Transaction;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.exception.InsufficientFundException;
import com.example.money.repository.AccountRepository;
import com.example.money.repository.TransactionRepository;
import com.example.money.service.AccountService;
import com.example.money.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @TestConfiguration
    static class TransactionServiceTestContextConfiguration {

        @Bean
        public TransactionService transactionService() {
            return new TransactionService();
        }
    }

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testCreateTransaction() throws Exception{
        long fromId = 2;
        long toId = 1;
        BigDecimal amount = new BigDecimal(10);

        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal(30));
        fromAccount.setId(2);
        fromAccount.setName("Andy");
        given(accountRepository.findById(fromId)).willReturn(fromAccount);

        Account toAccount = new Account();
        toAccount.setBalance(new BigDecimal(0));
        toAccount.setId(1);
        toAccount.setName("Sunny");
        given(accountRepository.findById(toId)).willReturn(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccountId(fromId);
        transaction.setToAccountId(toId);
        Calendar cal = Calendar.getInstance();
        transaction.setCreateTs(new Timestamp(cal.getTime().getTime()));

        Transaction actualTransaction = new Transaction();
        actualTransaction.setId(1);
        actualTransaction.setAmount(amount);
        actualTransaction.setFromAccountId(fromId);
        actualTransaction.setToAccountId(toId);
        actualTransaction.setCreateTs(new Timestamp(cal.getTime().getTime()));

        given(transactionRepository.save(transaction)).willReturn(actualTransaction);

        Account fromAccountAfter = new Account();
        fromAccountAfter.setBalance(new BigDecimal(20));
        fromAccountAfter.setId(2);
        fromAccountAfter.setName("Andy");

        Account toAccountAfter = new Account();
        toAccountAfter.setBalance(new BigDecimal(10));
        toAccountAfter.setId(1);
        toAccountAfter.setName("Sunny");


        given(accountRepository.save(fromAccountAfter)).willReturn(fromAccountAfter);
        given(accountRepository.save(toAccountAfter)).willReturn(toAccountAfter);

        transactionService.createTransaction(fromId,toId, amount);

        ArgumentCaptor<Account> argumentCaptor1 = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(2)).save(argumentCaptor1.capture());

        assertThat(argumentCaptor1.getAllValues().get(0).getBalance()).isEqualByComparingTo(new BigDecimal(20));
        assertThat(argumentCaptor1.getAllValues().get(1).getBalance()).isEqualByComparingTo(new BigDecimal(10));

    }

    @Test
    public void testCreateTransaction_fromAccountNotFound() throws Exception{
        long fromId = 3;
        long toId = 1;
        BigDecimal amount = new BigDecimal(10);

        given(accountRepository.findById(fromId)).willReturn(null);
        try {
            transactionService.createTransaction(fromId, toId, amount);
            fail("Should throw AccountNotFoundException");
        }catch (AccountNotFoundException e){
            assertThat(e.getAccountId()).isEqualTo(fromId);
        }
    }

    @Test
    public void testCreateTransaction_toAccountNotFound() throws Exception{
        long fromId = 2;
        long toId = 3;
        BigDecimal amount = new BigDecimal(10);

        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal(30));
        fromAccount.setId(2);
        fromAccount.setName("Andy");
        given(accountRepository.findById(fromId)).willReturn(fromAccount);

        given(accountRepository.findById(toId)).willReturn(null);

        try {
            transactionService.createTransaction(fromId, toId, amount);
            fail("Should throw AccountNotFoundException");
        }catch (AccountNotFoundException e){
            assertThat(e.getAccountId()).isEqualTo(toId);
        }
    }

    @Test
    public void testCreateTransaction_InsufficientFundException() throws Exception{
        long fromId = 3;
        long toId = 1;
        BigDecimal amount = new BigDecimal(100);
        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal(30));
        fromAccount.setId(2);
        fromAccount.setName("Andy");
        given(accountRepository.findById(fromId)).willReturn(fromAccount);
        try {
            transactionService.createTransaction(fromId, toId, amount);
            fail("Should throw InsufficientFundException");
        }catch (InsufficientFundException e){
            assertThat(e.getAccountId()).isEqualTo(fromId);
        }
    }
}
