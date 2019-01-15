package com.example.money.repository;

import com.example.money.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Override
    <S extends Transaction> S save(S s);
}
