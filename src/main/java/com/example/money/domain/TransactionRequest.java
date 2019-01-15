package com.example.money.domain;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionRequest {

    @ApiModelProperty(notes = "Account Id which transfer money from")
    private long fromId;
    @ApiModelProperty(notes = "Account Id which transfer money to")
    private long toId;
    @ApiModelProperty(notes = "Amount of money transfer")
    private BigDecimal amount;

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRequest that = (TransactionRequest) o;
        return fromId == that.fromId &&
                toId == that.toId &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, amount);
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", amount=" + amount +
                '}';
    }
}
