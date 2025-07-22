package com.rafiyad.bank.features.account.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String mobileNumber;
    private String accountType;
    private String bankName;
    private String branchName;
    private String country;
    private String city;
    private String address;
}
