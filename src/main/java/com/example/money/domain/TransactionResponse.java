package com.example.money.domain;

import org.springframework.hateoas.ResourceSupport;

public class TransactionResponse extends BaseResponse {

    String linkToBalance;

    public String getLinkToBalance() {
        return linkToBalance;
    }

    public void setLinkToBalance(String linkToBalance) {
        this.linkToBalance = linkToBalance;
    }
}
