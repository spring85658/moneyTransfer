package com.example.money.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.Objects;

public class BalanceResponse extends BaseResponse {

    @ApiModelProperty(notes = "The account balance")
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceResponse that = (BalanceResponse) o;
        return Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }
}
