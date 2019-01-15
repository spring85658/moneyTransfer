package com.example.money.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

public class BaseResourceSupportResponse extends ResourceSupport {

    @ApiModelProperty(notes = "Response code")
    private int code;
    @ApiModelProperty(notes = "Response message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
