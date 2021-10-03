package com.ironhack.midterm.bankingAPI.repository.transactions;

import com.ironhack.midterm.bankingAPI.dao.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query ("SELECT t FROM Transaction t WHERE sender_account_id = :id ORDER BY transactionDate Desc")
    List<Transaction> findBySenderOrderByTransactionDateDesc(Long id);
    @Query(value = "select max(sub.daily_sum) from (select sum(amount_transferred) as daily_sum from transactions group by day(transaction_date), sender_account_id) as sub;",nativeQuery = true)
    BigDecimal getMaxDailySum();
    @Query(value ="select sum(amount_transferred) from transactions where sender_account_id = :id and day(transaction_date) = day(curdate());", nativeQuery = true)
    BigDecimal getDailySumForId(Long id);
}
