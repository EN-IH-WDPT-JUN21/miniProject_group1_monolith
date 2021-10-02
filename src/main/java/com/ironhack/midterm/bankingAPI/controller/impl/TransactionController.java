package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.ITransactionController;
import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
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
    public Transaction transferFunds(@RequestBody @Valid TransactionDTO transactionDTO) {
        //Casting to userDetails, if we cast to Principal it gives casting error is the runtime, getPrincipal() method is confusing, it returns an object.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return transactionService.transferFunds(transactionDTO, username);
    }
}
