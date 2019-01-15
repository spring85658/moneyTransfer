package com.example.money.repository;

import com.example.money.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<Account, Long> {

    Account findById(long id);

    @Override
    <S extends Account> S save(S s);
}
