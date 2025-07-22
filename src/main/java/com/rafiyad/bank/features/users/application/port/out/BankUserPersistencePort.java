package com.rafiyad.bank.features.users.application.port.out;

import com.rafiyad.bank.features.users.domain.BankUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Flushable;

public interface BankUserPersistencePort {
    Flux<BankUser> getAllBankUser();
    Mono<BankUser> getUserByAccountNumber(String accountNumber);
    Mono<Boolean> createUser(String username, String accountNumber, String mobileNumber, String accountType);
    Mono<Boolean> updateUser(String username, String accountNumber, String mobileNumber, String accountType);
    Mono<Boolean> deleteUser(String accountNumber);
}
