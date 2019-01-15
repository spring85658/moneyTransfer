package com.example.money;

import com.example.money.domain.Account;
import com.example.money.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testWhenFindById(){
        Account account = new Account();
        account.setId(1);
        account.setBalance(new BigDecimal(0));
        account.setName("Sunny");

        Account dbResult = accountRepository.findById(1);

        assertThat(dbResult.getId()).isEqualTo(account.getId());
    }
}
