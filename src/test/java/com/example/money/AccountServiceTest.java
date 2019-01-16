package com.example.money;

import com.example.money.domain.Account;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.repository.AccountRepository;
import com.example.money.service.AccountService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @TestConfiguration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountService();
        }
    }

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testGetAccountBalanceById() throws Exception{
        Account result = new Account();
        result.setName("Sunny");
        result.setId(1);
        result.setBalance(BigDecimal.ZERO);

        given(accountRepository.findById(1)).willReturn(result);

        BigDecimal actualResult = accountService.getAccountBalanceById(1);

        assertThat(actualResult).isEqualByComparingTo(result.getBalance());
    }

    @Test
    public void testGetAccountBalanceById_accountNotFound() throws Exception{
        Account result = new Account();
        result.setName("Sunny");
        result.setId(3);
        result.setBalance(BigDecimal.ZERO);

        given(accountRepository.findById(3)).willReturn(null);
        try {
            BigDecimal actualResult = accountService.getAccountBalanceById(3);
            fail("Should throw AccountNotFoundException");
        }catch (AccountNotFoundException e){
            assertThat(e.getAccountId()).isEqualTo(result.getId());
        }
    }
}
