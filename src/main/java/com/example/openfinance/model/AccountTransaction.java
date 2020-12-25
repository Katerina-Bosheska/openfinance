package com.example.openfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    //GETTERS

    public int getId() {
        return Id;
    }

    public Account getPayer() {
        return payer;
    }

    public Account getRecipient() {
        return recipient;
    }

    public String getKonto() {
        return konto;
    }

    public String getProgram() {
        return program;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    //SETTERS

    public void setId(int id) {
        Id = id;
    }

    public void setPayer(Account payer) {
        this.payer = payer;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
