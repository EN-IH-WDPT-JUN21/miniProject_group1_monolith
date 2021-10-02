package com.ironhack.midterm.bankingAPI.dao.transactions;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.roles.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "sender_account_id",insertable=false, updatable=false)
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "sender_account_id", insertable=false, updatable=false)
    private Account receiver;
    private Date transactionDate;
    private BigDecimal amountTransferred;

    public Transaction(Account sender, Account receiver, BigDecimal amountTransferred) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountTransferred = amountTransferred;
        transactionDate = new Date();
    }
}
