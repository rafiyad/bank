package com.rafiyad.bank.features.account.adapter;

import com.rafiyad.bank.features.account.adapter.out.repository.AccountRepository;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import lombok.Builder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Builder
public class AccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountRepository accountRepository;

    public AccountPersistenceAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return accountRepository.findAll().map(ac ->
                new Account(ac.getId(), ac.getAccountNumber(), ac.getBalance()));
    }

    @Override
    public Mono<BigDecimal> checkBalance(String accountNumber) {
        return null;
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        return null;
    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }

    //Convert Entity to Domain

}
