package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;

public interface ITransactionService {
    Transaction transferFunds(TransactionDTO transactionDTO, String username);
}
