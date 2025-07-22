package com.rafiyad.bank.features.account.application.port.out;


import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.domain.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountPersistencePort {
    Flux<Account> findAllAccounts();
    Mono<Account> findAccountByAccountNumber(String accountNumber);
    Mono<BigDecimal> checkBalance(String accountNumber);
    Mono<Account> createAccount(Account account);
    Mono<BigDecimal> addBalance(Account account, BigDecimal amount);
}
