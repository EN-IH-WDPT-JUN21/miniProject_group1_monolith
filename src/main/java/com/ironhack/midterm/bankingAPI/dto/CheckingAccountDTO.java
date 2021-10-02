package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccountDTO {
    private BigDecimal balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String secretKey;

    public CheckingAccountDTO(BigDecimal balance, Long primaryOwnerId, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secretKey = secretKey;
    }
}
