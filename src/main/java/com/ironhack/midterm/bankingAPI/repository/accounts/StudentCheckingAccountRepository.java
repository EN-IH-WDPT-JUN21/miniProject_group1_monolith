package com.ironhack.midterm.bankingAPI.repository.accounts;

import com.ironhack.midterm.bankingAPI.dao.accounts.StudentCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCheckingAccountRepository extends JpaRepository<StudentCheckingAccount,Long> {
}
