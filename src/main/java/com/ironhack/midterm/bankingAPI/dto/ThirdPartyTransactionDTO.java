package com.ironhack.midterm.bankingAPI.dto;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyTransactionDTO {
    @NotNull(message = "Account muse be set")
    private Long accountId;
    @NotNull(message = "Amount muse be set")
    private BigDecimal amount;
    @NotNull(message = "Secret key must be set")
    private String secretKey;
}
