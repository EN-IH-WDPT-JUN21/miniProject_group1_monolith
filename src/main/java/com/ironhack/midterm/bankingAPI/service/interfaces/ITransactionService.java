package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;

public interface ITransactionService {
    TransactionConfirmationDTO transferFunds(TransactionDTO transactionDTO, String username);
}
