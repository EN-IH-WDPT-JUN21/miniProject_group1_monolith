package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionConfirmationDTO {
    private Long senderId;
    @NotNull(message = "Sender name must be set")
    private String senderName;
    private Long receiverId;
    @NotNull(message = "Receiver name must be set")
    private String receiverName;
    @NotNull(message = "Transaction must have a date")
    private Date transactionDate;
    @NotNull(message = "Amount must be set in a transaction")
    private BigDecimal amount;
}
