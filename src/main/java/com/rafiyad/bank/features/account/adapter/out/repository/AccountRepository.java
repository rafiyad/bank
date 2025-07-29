package com.rafiyad.bank.features.account.adapter.out.repository;

import com.rafiyad.bank.features.account.adapter.out.entity.AccountEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    @Query("SELECT * FROM account WHERE account_number = :accountNumber")
    Mono<AccountEntity> findByAccountNumber(String accountNumber);

    Mono<Void> deleteByAccountNumber(String accountNumber);


}
