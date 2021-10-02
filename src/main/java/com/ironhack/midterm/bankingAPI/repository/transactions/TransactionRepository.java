package com.ironhack.midterm.bankingAPI.repository.transactions;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySenderOrderByTransactionDateDesc(Long id);
}
