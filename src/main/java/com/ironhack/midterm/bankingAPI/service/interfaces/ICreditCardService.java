package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import com.ironhack.midterm.bankingAPI.dto.CreditCardDTO;

public interface ICreditCardService {
    CreditCard saveCC(CreditCardDTO creditCardDTO);
}
