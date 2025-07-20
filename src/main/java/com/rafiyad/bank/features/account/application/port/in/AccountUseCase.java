package com.rafiyad.bank.features.account.application.port.in;


import com.rafiyad.bank.features.account.domain.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountUseCase {
    Flux<Account> findAllAccounts();
    Mono<BigDecimal> checkBalance();
    Mono<Account> createAccount(Account account);
    Mono<BigDecimal> addBalance(Account account, BigDecimal amount);

}
