package com.example.money.domain;

import java.util.Map;

public class ErrorResponse extends BaseResponse {

    public ErrorResponse(String msg){
        this.setMessage(msg);
    }
}
