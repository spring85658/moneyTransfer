package com.example.money;

import com.example.money.controller.AccountController;
import com.example.money.controller.TransactionController;
import com.example.money.domain.ApiCode;
import com.example.money.domain.Transaction;
import com.example.money.domain.TransactionRequest;
import com.example.money.domain.TransactionResponse;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.exception.InsufficientFundException;
import com.example.money.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTransaction() throws Exception{
        long fromId = 2;
        long toId = 1;
        BigDecimal amount = new BigDecimal(10);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFromId(fromId);
        transactionRequest.setToId(toId);
        transactionRequest.setAmount(amount);

        Transaction transaction = new Transaction();

        given(transactionService.createTransaction(fromId, toId, amount)).willReturn(transaction);

        mvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isCreated());




    }

    @Test
    public void testCreateTransaction_accountNotFound() throws Exception{
        long fromId = 3;
        long toId = 1;
        BigDecimal amount = new BigDecimal(10);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFromId(fromId);
        transactionRequest.setToId(toId);
        transactionRequest.setAmount(amount);


        given(transactionService.createTransaction(fromId, toId, amount)).willThrow(AccountNotFoundException.class);

        MvcResult result = mvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isNotFound()).andReturn();
        TransactionResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TransactionResponse.class);
        assertThat(response.getCode()).isEqualTo(ApiCode.ACCOUNT_NOT_FOUND);
    }

    @Test
    public void testCreateTransaction_insufficientFund() throws Exception{
        long fromId = 3;
        long toId = 1;
        BigDecimal amount = new BigDecimal(30);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFromId(fromId);
        transactionRequest.setToId(toId);
        transactionRequest.setAmount(amount);

        given(transactionService.createTransaction(fromId, toId, amount)).willThrow(InsufficientFundException.class);

        MvcResult result = mvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isUnprocessableEntity()).andReturn();
        TransactionResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TransactionResponse.class);
        assertThat(response.getCode()).isEqualTo(ApiCode.INSUFFICIENT_FUND);
    }
}
