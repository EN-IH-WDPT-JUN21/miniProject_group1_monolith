package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingAccountDTO {
    private BigDecimal balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;
    private String secretKey;

    public SavingAccountDTO(BigDecimal balance, Long primaryOwnerId, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
    }
}
