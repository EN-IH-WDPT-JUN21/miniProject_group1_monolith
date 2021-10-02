package com.ironhack.midterm.bankingAPI.repository.transactions;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
