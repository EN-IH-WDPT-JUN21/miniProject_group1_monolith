package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.ITransactionController;
import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;
import com.ironhack.midterm.bankingAPI.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class TransactionController implements ITransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer_funds")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransactionConfirmationDTO transferFunds(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.transferFunds(transactionDTO);
    }
}
