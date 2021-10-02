package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @NotNull(message="Sender id must be passed")
    private Long senderId;
    @NotNull(message="Receiver id must be passed")
    private Long receiverId;
    @NotNull(message="amount transferred must be passed")
    private BigDecimal amountTransferred;
    @NotNull(message="Primary owner name must be passed")
    private String primaryOwnerName;
    private String secondaryOwnerName;

    public TransactionDTO(Long senderId, Long receiverId, BigDecimal amountTransferred, String primaryOwnerName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountTransferred = amountTransferred;
        this.primaryOwnerName = primaryOwnerName;
    }
}

