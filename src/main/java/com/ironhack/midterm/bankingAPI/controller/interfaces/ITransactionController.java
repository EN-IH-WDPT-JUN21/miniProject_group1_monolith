package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;

public interface ITransactionController {
    Transaction transferFunds(TransactionDTO transactionDTO);
}
