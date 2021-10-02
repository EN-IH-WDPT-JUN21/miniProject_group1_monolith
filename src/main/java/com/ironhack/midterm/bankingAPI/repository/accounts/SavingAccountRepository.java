package com.ironhack.midterm.bankingAPI.repository.accounts;

import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount,Long> {
}
