package com.example.openfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
public class AccountTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account payer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account recipient;

    //payer's
    @Column(columnDefinition="nvarchar(255)")
    private String konto;

    @Column(columnDefinition="nvarchar(255)")
    private String program;

    @Column
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;

    @Column
    private double amount;

    public AccountTransaction(){}

    public AccountTransaction(Account payer, Account recipient, String konto, String program, double amount, LocalDate date) {
        this.payer = payer;
        this.recipient = recipient;
        this.konto = konto;
        this.amount = amount;
        this.program = program;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AccountTransaction))
            return false;
        AccountTransaction other = (AccountTransaction) o;
        return this.Id == other.getId();
    }

}
