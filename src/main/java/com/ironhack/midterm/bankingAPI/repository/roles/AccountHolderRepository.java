package com.ironhack.midterm.bankingAPI.repository.roles;

import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder,Long> {
}
