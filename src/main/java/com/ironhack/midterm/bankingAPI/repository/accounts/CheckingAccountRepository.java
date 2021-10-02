package com.ironhack.midterm.bankingAPI.repository.accounts;

import com.ironhack.midterm.bankingAPI.dao.accounts.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount,Long> {
}
