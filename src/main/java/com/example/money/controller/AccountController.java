package com.example.money.controller;

import com.example.money.domain.*;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.service.AccountService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("account")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Get the account balance by account id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved account balance", response = BalanceResponse.class),
        @ApiResponse(code = 404, message = "Account Not Found", response = ErrorResponse.class)
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<? extends BaseResponse> getBalance(@ApiParam(value = "Account Id", required = true) @PathVariable long id) {
        BigDecimal balance = null;
        try {
            balance = accountService.getAccountBalanceById(id);
        }catch (AccountNotFoundException ex){
            ErrorResponse errorResponse = new ErrorResponse("Account not found");
            errorResponse.setCode(ApiCode.ACCOUNT_NOT_FOUND);
            ResponseEntity<ErrorResponse> errorResponseEntity = new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
            return errorResponseEntity;
        }
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(balance);
        balanceResponse.setCode(ApiCode.SUCCESS);
        ResponseEntity<BalanceResponse> responseEntity = new ResponseEntity<>(balanceResponse, new HttpHeaders(), HttpStatus.OK);
        return responseEntity;
    }
}
