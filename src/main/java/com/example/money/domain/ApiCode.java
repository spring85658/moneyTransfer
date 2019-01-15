package com.example.money.domain;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum ApiCode {
    SUCCESS(0),
    ACCOUNT_NOT_FOUND(-1),
    INSUFFICIENT_FUND(-2);

    int code;

    ApiCode(int code){
        this.code = code;
    }

    public int getValue(){
        return code;
    }
}
