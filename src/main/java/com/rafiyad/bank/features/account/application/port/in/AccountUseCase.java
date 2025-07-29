package com.rafiyad.bank.features.account.application.port.in;


import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.domain.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountUseCase {
    Flux<AccountResponseDto> findAllAccounts();
    Mono<AccountResponseDto> findAccountByAccountNumber(String accountNumber);
    Mono<AccountResponseDto> createAccount(AccountRequestDto accountRequestDto);
    Mono<AccountResponseDto> deleteAccountByAccountNumber(String accountNumber, String mobileNumber);
    Mono<AccountResponseDto> updateAccountByMobileNumber(String accountNumber, AccountRequestDto accountRequestDto);
    Mono<BigDecimal> checkBalance();
    Mono<BigDecimal> addBalance(Account account, BigDecimal amount);

}
