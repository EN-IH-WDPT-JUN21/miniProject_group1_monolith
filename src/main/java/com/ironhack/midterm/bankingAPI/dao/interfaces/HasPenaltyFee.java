package com.ironhack.midterm.bankingAPI.dao.interfaces;

public interface HasPenaltyFee {
    boolean balanceBelowMinimum();
    void chargeFee();
}
