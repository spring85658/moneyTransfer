package com.example.money.exception;

public class InsufficientFundException extends Exception {

    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public InsufficientFundException() {
    }

    public InsufficientFundException(String message) {
        super(message);
    }

    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
}
