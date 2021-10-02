package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.repository.transactions.TransactionRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction transferFunds(TransactionDTO transactionDTO) {
        //validate if the user has access to the sender account

        //validate if the owner and secondary owner names are correct

        //validate if the receiver exists

        //validate if the account has sufficient funds
    }
}
