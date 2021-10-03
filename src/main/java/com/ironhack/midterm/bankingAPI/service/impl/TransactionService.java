package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.TransactionConfirmationBuilder;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.repository.transactions.TransactionRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    public TransactionConfirmationDTO transferFunds(TransactionDTO transactionDTO) {
        //Casting to userDetails, if we cast to Principal it gives casting error is the runtime, getPrincipal() method is confusing, it returns an object.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
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
        //validate if the receiver exists
        Optional<Account> receiverAccount = accountRepository.findById(transactionDTO.getReceiverId());
        if (receiverAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Receiver account not found!");
        }
        //validate if the owner and secondary owner of the receiving account names are correct
        boolean primaryOwnerNameIsValid = transactionDTO.getPrimaryOwnerName().equals(receiverAccount.get().getPrimaryOwner().getName());
        boolean secondaryOwnerNameIsValid = false;
        if (transactionDTO.getSecondaryOwnerName()==null && receiverAccount.get().getSecondaryOwner()==null){
            secondaryOwnerNameIsValid = true;
        }else if(receiverAccount.get().getSecondaryOwner()!=null && receiverAccount.get().getSecondaryOwner().getUsername()==transactionDTO.getSecondaryOwnerName()){
            secondaryOwnerNameIsValid = true;
        }
        if (!primaryOwnerNameIsValid || !secondaryOwnerNameIsValid){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Owners names don't match!");
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
        //Check for fraud, this one I modified a bit, because it would block the system if the transactions table would be empty, and you would post 2 transactions.
        //First for 10$ and 2nd for 100$. The first would be successful because it would be the first transaction in the db.
        //The 2nd transaction would be blocked, because the highest total of every customer would be 10, because there's only one transaction available.
        BigDecimal LOWEST_FRAUD_DETECTION_AMOUNT = new BigDecimal("100000");
        BigDecimal customersMaxSum = transactionRepository.getMaxDailySum();
        BigDecimal thisCustomerTodaySum = transactionRepository.getDailySumForId(transactionDTO.getSenderId());
        //with empty db this values can be empty
        if (customersMaxSum != null && thisCustomerTodaySum != null){
            //1 means that thisCustomerTodaySum is greater than customers MaxSum * 1.5 and is greater than the LOWEST_FRAUD_DETECTION_AMOUNT
            if(
                transactionDTO.getAmountTransferred().compareTo(LOWEST_FRAUD_DETECTION_AMOUNT)==1 &&
                thisCustomerTodaySum.add(transactionDTO.getAmountTransferred()).compareTo(customersMaxSum.multiply(new BigDecimal("1.5")))==1
            ){
                    //Freeze account and save it's state
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
    private TransactionConfirmationDTO transferVerifiedFunds(TransactionDTO transactionDTO){
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
        //Penalty Fee checkpoint
        PenaltyFeeService.penaltyFeeChargeService(sender.get());
        //Build transaction object
        Transaction transaction = new Transaction(sender.get(),receiver.get(),transactionDTO.getAmountTransferred());
        //Save everything
        transactionRepository.save(transaction);
        accountRepository.save(sender.get());
        accountRepository.save(receiver.get());
        //Build and return transaction confirmation
        return TransactionConfirmationBuilder.buildForAccountHolders(transactionDTO,sender.get().getPrimaryOwner().getName(), transaction.getTransactionDate());
    }
}
