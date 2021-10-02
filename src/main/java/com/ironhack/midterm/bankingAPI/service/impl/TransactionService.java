package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.repository.transactions.TransactionRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    public Transaction transferFunds(TransactionDTO transactionDTO, String username) {
        Optional<Account> senderAccount =accountRepository.findById(transactionDTO.getSenderId());
        //validate if the sender account exists
        if (senderAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender account not found!");
        }
        //validate if the user has access to the sender account
        boolean isOwnerOfTheSenderAccount = false;
        //First if checks for the primary owner, if not equal then checks secondary owner in the else statement
        if(senderAccount.get().getPrimaryOwner().getUsername().equals(username)){
          isOwnerOfTheSenderAccount = true;
        }else{
            //if this code executes that means that the primary owner is not the user logged in, checking the secoundary owner
            if (senderAccount.get().getSecondaryOwner()!=null && senderAccount.get().getSecondaryOwner().getUsername().equals(username)){
                isOwnerOfTheSenderAccount = true;
            }
        }
        if (!isOwnerOfTheSenderAccount){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have access to this account!");
        }
        //validate if the owner and secondary owner names are correct
        boolean primaryOwnerNameIsValid = transactionDTO.getPrimaryOwnerName().equals(senderAccount.get().getPrimaryOwner().getName());
        boolean secondaryOwnerNameIsValid = false;
        if (transactionDTO.getSecondaryOwnerName()==null && senderAccount.get().getSecondaryOwner()==null){
            secondaryOwnerNameIsValid = true;
        }else if(senderAccount.get().getSecondaryOwner()!=null && senderAccount.get().getSecondaryOwner().getUsername()==transactionDTO.getSecondaryOwnerName()){
            secondaryOwnerNameIsValid = true;
        }
        if (!primaryOwnerNameIsValid || !secondaryOwnerNameIsValid){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Owners names don't match!");
        }
        //validate if the receiver exists
        Optional<Account> receiverAccount = accountRepository.findById(transactionDTO.getReceiverId());
        if (receiverAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Receiver account not found!");
        }
        //validate if both accounts are ACTIVE
        if (!senderAccount.get().isActive() || !receiverAccount.get().isActive()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"One of the accounts is not active");
        }
        //validate if the account has sufficient funds
        //-1 means that the sender balance is less than amount transferred
        if (senderAccount.get().getBalance().compareTo(transactionDTO.getAmountTransferred())==-1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough funds!");
        }
        //Check for fraud - 2 transactions in less than second.
        List<Transaction> userTransactions = transactionRepository.findBySenderOrderByTransactionDateDesc(transactionDTO.getSenderId());
        if (userTransactions.size()!=0){
            long millisecondsSinceLastUserTransaction = new Date().getTime()-userTransactions.get(0).getTransactionDate().getTime();
            if (millisecondsSinceLastUserTransaction<1000){
            senderAccount.get().freezeAccount();
            accountRepository.save(senderAccount.get());
            }
        }
        //Checking one more time, because Credit Cards cannot be frozen and the transaction continues
        if (!senderAccount.get().isActive()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Fraud detected, freezing account");
        }
        //code with @Transactional annotation separated to own method
        return transferVerifiedFunds(transactionDTO);
    }
    //Moving funds, creating objects and saving them
    @Transactional
    private Transaction transferVerifiedFunds(TransactionDTO transactionDTO){
        //Transfer money
        Optional<Account> receiver = accountRepository.findById(transactionDTO.getReceiverId());
        if (receiver.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"This shouldn't have happened!");
        }
        //Adding balance to the receiver account
        receiver.get().setBalance(receiver.get().getBalance().add(transactionDTO.getAmountTransferred()));
        Optional<Account> sender = accountRepository.findById(transactionDTO.getSenderId());
        if (sender.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"This shouldn't have happened!");
        }
        //subtracting funds from the sender account, it was verified that he has sufficient balance
        sender.get().setBalance(sender.get().getBalance().subtract(transactionDTO.getAmountTransferred()));
        //Build transaction object
        Transaction transaction = new Transaction(sender.get(),receiver.get(),transactionDTO.getAmountTransferred());
        //Save everything
        transactionRepository.save(transaction);
        accountRepository.save(sender.get());
        accountRepository.save(receiver.get());
        //End transaction
        return transaction;
    }
}
