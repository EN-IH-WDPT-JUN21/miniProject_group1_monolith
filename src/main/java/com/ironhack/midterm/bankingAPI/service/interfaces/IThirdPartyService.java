package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;

public interface IThirdPartyService {
    ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);

    TransactionConfirmationDTO sendFunds(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String hashKey);

    TransactionConfirmationDTO receiveFunds(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String hashKey);
}
