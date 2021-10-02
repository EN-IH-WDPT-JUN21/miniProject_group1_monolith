package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionDTO;

import java.util.Date;

public class TransactionConfirmationBuilder {
    public static TransactionConfirmationDTO buildForAccountHolders(TransactionDTO transactionDTO, String receiverName, Date transactionDate){
        TransactionConfirmationDTO transactionConfirmationDTO = new TransactionConfirmationDTO();

        transactionConfirmationDTO.setSenderId(transactionDTO.getSenderId());
        transactionConfirmationDTO.setSenderName(transactionDTO.getPrimaryOwnerName());
        transactionConfirmationDTO.setReceiverId(transactionDTO.getReceiverId());
        transactionConfirmationDTO.setReceiverName(receiverName);
        transactionConfirmationDTO.setTransactionDate(transactionDate);
        transactionConfirmationDTO.setAmount(transactionDTO.getAmountTransferred());

        return transactionConfirmationDTO;
    }
    public static TransactionConfirmationDTO buildForSendingThirdParty(ThirdPartyTransactionDTO thirdPartyTransactionDTO, Account account){
        TransactionConfirmationDTO transactionConfirmationDTO = new TransactionConfirmationDTO();

        transactionConfirmationDTO.setSenderName("THIRD PARTY SENDER");
        transactionConfirmationDTO.setReceiverName(account.getPrimaryOwner().getName());
        transactionConfirmationDTO.setReceiverId(account.getId());
        transactionConfirmationDTO.setAmount(thirdPartyTransactionDTO.getAmount());
        transactionConfirmationDTO.setTransactionDate(new Date());

        return transactionConfirmationDTO;
    }
    public static TransactionConfirmationDTO buildForReceivingThirdParty(ThirdPartyTransactionDTO thirdPartyTransactionDTO,Account account){
        TransactionConfirmationDTO transactionConfirmationDTO = new TransactionConfirmationDTO();

        transactionConfirmationDTO.setReceiverName("THIRD PARTY RECEIVER");
        transactionConfirmationDTO.setSenderName(account.getPrimaryOwner().getName());
        transactionConfirmationDTO.setSenderId(account.getId());
        transactionConfirmationDTO.setAmount(thirdPartyTransactionDTO.getAmount());
        transactionConfirmationDTO.setTransactionDate(new Date());

        return transactionConfirmationDTO;
    }
}
