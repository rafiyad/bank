package com.rafiyad.bank.features.account.adapter.out.repository;

import com.rafiyad.bank.features.account.adapter.out.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    Mono<BigDecimal> findByAccountNumber(String accountNumber);

}
