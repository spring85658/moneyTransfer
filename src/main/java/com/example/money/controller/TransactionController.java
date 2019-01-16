package com.example.money.controller;


import com.example.money.domain.*;
import com.example.money.exception.AccountNotFoundException;
import com.example.money.exception.InsufficientFundException;
import com.example.money.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Create transfer money transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved account balance", response = TransactionResponse.class),
            @ApiResponse(code = 404, message = "Account Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Insufficient fund for account", response = ErrorResponse.class)
    })
    @PostMapping()
    public ResponseEntity<? extends BaseResponse> createTransaction(@RequestBody TransactionRequest transactionRequest){
        logger.info("Transfer money {}", transactionRequest);
        try {
            Transaction transaction = transactionService.createTransaction(transactionRequest.getFromId(), transactionRequest.getToId(), transactionRequest.getAmount());
        } catch (AccountNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Account not found");
            errorResponse.setCode(ApiCode.ACCOUNT_NOT_FOUND);
            ResponseEntity<ErrorResponse> errorResponseEntity = new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
            return errorResponseEntity;
        } catch (InsufficientFundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Insufficient fund for account id "+ e.getAccountId());
            errorResponse.setCode(ApiCode.INSUFFICIENT_FUND);
            ResponseEntity<ErrorResponse> errorResponseEntity = new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
            return errorResponseEntity;
        }

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCode(ApiCode.SUCCESS);

        Link link = linkTo(methodOn(AccountController.class).getBalance(transactionRequest.getFromId())).withSelfRel();
        transactionResponse.setLinkToBalance(link.getHref());
        ResponseEntity<TransactionResponse> responseEntity = new ResponseEntity<>(transactionResponse, new HttpHeaders(), HttpStatus.CREATED);
        return responseEntity;
    }
}
