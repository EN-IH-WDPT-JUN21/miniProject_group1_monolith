package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.interfaces.HasPenaltyFee;

public class PenaltyFeeService {
    public static void penaltyFeeChargeService(Account account){
        if (account instanceof HasPenaltyFee) {
            HasPenaltyFee accountWithPenaltyFee = (HasPenaltyFee) account;
            if (accountWithPenaltyFee.balanceBelowMinimum()){
                accountWithPenaltyFee.chargeFee();
            }
        }
    }
}
