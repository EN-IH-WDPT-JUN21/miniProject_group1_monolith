package com.ironhack.midterm.bankingAPI.repository.accounts;

import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long>{
}
