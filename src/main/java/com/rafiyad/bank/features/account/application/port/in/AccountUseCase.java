package com.rafiyad.bank.features.account.application.port.in;


import com.rafiyad.bank.features.account.application.port.in.dto.request.RequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.domain.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountUseCase {
    Mono<AccountResponseDto> getNumbers();
    Mono<AccountResponseDto> numbersFind(Integer id);
    Mono<AccountResponseDto> addNumber(RequestDto requestDto);
    Flux<AccountResponseDto> findAllAccounts();
    Mono<AccountResponseDto> findAccountByAccountNumber(String accountNumber);
    Mono<BigDecimal> checkBalance();
    Mono<Account> createAccount(Account account);
    Mono<BigDecimal> addBalance(Account account, BigDecimal amount);

}
