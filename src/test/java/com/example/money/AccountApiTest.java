package com.example.money;

import com.example.money.controller.AccountController;
import com.example.money.domain.ApiCode;
import com.example.money.domain.BalanceResponse;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AccountController.class)
public class AccountApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testGetBalance() throws Exception{
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(BigDecimal.ZERO);
        balanceResponse.setCode(ApiCode.SUCCESS);
        balanceResponse.setMessage(null);

        given(accountService.getAccountBalanceById(1)).willReturn(balanceResponse.getBalance());

        mvc.perform(get("/account/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(balanceResponse.getBalance()));

    }

    @Test
    public void testGetBalance_accountNotExist() throws Exception{
        given(accountService.getAccountBalanceById(3)).willThrow(AccountNotFoundException.class);

        mvc.perform(get("/account/3")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ApiCode.ACCOUNT_NOT_FOUND.toString()));

    }
}
