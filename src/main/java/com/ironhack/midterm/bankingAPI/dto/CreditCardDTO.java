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
public class CreditCardDTO {
    private BigDecimal balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private BigDecimal creditLimit;
    private BigDecimal interestRate;

    public CreditCardDTO(BigDecimal balance, Long primaryOwnerId, BigDecimal creditLimit, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
}
