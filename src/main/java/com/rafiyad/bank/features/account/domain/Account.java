package com.rafiyad.bank.features.account.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String id;
    private String accountNumber;
    private String mobileNumber;
    private String email;
    private BigDecimal balance;
    private String accountType;
    private String branchName;
    private String bankName;


}
