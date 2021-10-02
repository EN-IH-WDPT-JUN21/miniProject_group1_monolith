package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import com.ironhack.midterm.bankingAPI.dto.CreditCardDTO;

public interface ICreditCardController {
    CreditCard saveCC(CreditCardDTO creditCardDTO);
}
