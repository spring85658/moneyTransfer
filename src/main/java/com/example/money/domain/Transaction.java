package com.example.money.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "from_account_id")
    private long fromAccountId;
    @Column(name = "to_account_id")
    private long toAccountId;
    @Column
    private BigDecimal amount;
    @Column(name = "create_ts")
    private Timestamp createTs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                fromAccountId == that.fromAccountId &&
                toAccountId == that.toAccountId &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(createTs, that.createTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccountId, toAccountId, amount, createTs);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", createTs=" + createTs +
                '}';
    }
}
