package com.example.money.exception;

public class AccountNotFoundException extends Exception {

    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public AccountNotFoundException(String errorMessage, long accountId){
        super(errorMessage);
        this.accountId = accountId;

    }

    public AccountNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
