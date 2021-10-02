package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.ThirdPartyBuilder;
import com.ironhack.midterm.bankingAPI.builders.TransactionConfirmationBuilder;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dao.roles.User;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.ThirdPartyRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class ThirdPartyService implements IThirdPartyService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AccountRepository accountRepository;

    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO) {
        if (userRepository.findByUsername(thirdPartyDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(BAD_REQUEST,"Username already exists!");
        }else {
            return thirdPartyRepository.save(ThirdPartyBuilder.build(thirdPartyDTO));
        }

    }

    public TransactionConfirmationDTO sendFunds(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String hashedKey) {
        //Setup separated to own method, because receiving funds mimics the large part of the logic
        transactionSetup(thirdPartyTransactionDTO,hashedKey);
        //transfer funds in separate method with @Transactional annotation
        return transferVerifiedFunds(thirdPartyTransactionDTO,true);
    }

    public TransactionConfirmationDTO receiveFunds(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String hashedKey) {
        //Setup separated to own method, because receiving funds mimics the large part of the logic
        transactionSetup(thirdPartyTransactionDTO,hashedKey);
        //transfer funds in separate method with @Transactional annotation
        return transferVerifiedFunds(thirdPartyTransactionDTO,false);
    }
    private void transactionSetup(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String hashedKey){
        //validate hashedKey
            //Casting to userDetails, if we cast to Principal it gives casting error is the runtime, getPrincipal() method is confusing, it returns an object.
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(username);
            //We're logged with third party so we can do an unchecked get() with casting to ThirdParty
            ThirdParty thirdParty = (ThirdParty) user.get();
            if (!hashedKey.equals(thirdParty.getHashKey())){
                throw new ResponseStatusException(BAD_REQUEST,"Invalid credentials!");
            }
        //check if the account exists
            Optional<Account> account = accountRepository.findById(thirdPartyTransactionDTO.getAccountId());
            if (account.isEmpty()){
                throw new ResponseStatusException(BAD_REQUEST,"Account doesn't exist!");
            }
        //check if the account is active
            if (!account.get().isActive()){
                throw new ResponseStatusException(FORBIDDEN,"Account is not active");
            }

        //check if the secret key is valid
            if (!account.get().secretKeyIsValid(thirdPartyTransactionDTO.getSecretKey())){
                throw new ResponseStatusException(BAD_REQUEST,"Invalid credentials!");
            }
    }

    @Transactional
    private TransactionConfirmationDTO transferVerifiedFunds(ThirdPartyTransactionDTO thirdPartyTransactionDTO, boolean sendingFunds){
        //send or receive funds
        Optional<Account> account = accountRepository.findById(thirdPartyTransactionDTO.getAccountId());
        if (sendingFunds){
            account.get().setBalance(account.get().getBalance().add(thirdPartyTransactionDTO.getAmount()));
        }
        //sending == false, means it's a receiving transaction, need to check if the account has enough funds
        else {
            //==-1 means that the new balance would be less than new BigDecimal("0")
            if (account.get().getBalance().subtract(thirdPartyTransactionDTO.getAmount()).compareTo(new BigDecimal("0"))==-1){
                throw new ResponseStatusException(FORBIDDEN,"Account has not enough funds for this operation");
            }
           account.get().setBalance(account.get().getBalance().subtract(thirdPartyTransactionDTO.getAmount()));
        }
        //save state
            accountRepository.save(account.get());
        //build and return object
            if (sendingFunds){
                return TransactionConfirmationBuilder.buildForSendingThirdParty(thirdPartyTransactionDTO, account.get());
            }else{
                return TransactionConfirmationBuilder.buildForReceivingThirdParty(thirdPartyTransactionDTO,account.get());
            }
    }
}
