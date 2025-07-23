package com.rafiyad.bank.features.account.application.port.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AccountRequestDto {

    private String mobileNumber;
    private String email;
    private String accountType;
    private String bankName;
    private String city;
    private String searchKey;

}
