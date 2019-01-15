package com.example.money.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

public class BaseResponse {

    @ApiModelProperty(notes = "Response code")
    @JsonProperty
    private ApiCode code;
    @ApiModelProperty(notes = "Response message")
    private String message;

    public ApiCode getCode() {
        return code;
    }

    public void setCode(ApiCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
