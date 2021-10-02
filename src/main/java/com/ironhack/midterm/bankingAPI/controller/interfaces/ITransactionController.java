package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;

public interface ITransactionController {
    TransactionConfirmationDTO transferFunds(TransactionDTO transactionDTO);
}
