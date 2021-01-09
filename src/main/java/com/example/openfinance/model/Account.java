package com.example.openfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition="nvarchar(255)", unique = true)
    @NotNull
    private String name;

    @Column
    @NotNull
    private String EDB;

    @JsonIgnore
    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions;

    @JsonIgnore
    @OneToMany(mappedBy = "budgetUser", cascade = CascadeType.ALL)
    private List<Budget> budgetUsage;

    @Column(columnDefinition="nvarchar(255)")
    @NotNull
    private String billNumber;
    @Column(columnDefinition="nvarchar(255)")
    private String address;
    @Column
    private double paidAmount;
    @Column
    private double receivedAmount;
    @Column
    private int type;
    // 1=javna ustanova, 2=kompanija, 3=poedinec

    public Account(){
        this.transactions = new ArrayList<>();
        this.budgetUsage = new ArrayList<>();
    }

    public Account(String name, String EDB, String billNumber, String address, int type, double paidAmount, double receivedAmount) {
        this.EDB = EDB;
        this.name = name;
        this.billNumber = billNumber;
        this.paidAmount = paidAmount;
        this.receivedAmount = receivedAmount;
        this.address = address;
        this.type = type;
        this.transactions = new ArrayList<>();
        this.budgetUsage = new ArrayList<>();
    }

    public Account(String name, String billNumber){
        this.name = name;
        this.billNumber = billNumber;
    }

    // GETTERS

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEDB() {
        return EDB;
    }

    public List<AccountTransaction> getTransactions() {
        return transactions;
    }

    public List<Budget> getBudgetUsage() {
        return budgetUsage;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getReceivedAmount() {
        return receivedAmount;
    }

    public int getType() { return type; }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id){ this.id = id;}

    public void setEDB(String EDB) {
        this.EDB = EDB;
    }

    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
    }

    public void setBudgetUsage(List<Budget> budgetUsage) {
        this.budgetUsage = budgetUsage;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public void applyAccount(Account account){
        this.name = account.getName();
        this.EDB = account.getEDB();
        this.address = account.getAddress();
        this.billNumber = account.getBillNumber();
        this.paidAmount = account.getPaidAmount();
        this.receivedAmount = account.getReceivedAmount();
        this.type = account.getType();
        this.transactions = account.getTransactions();
        this.budgetUsage = account.getBudgetUsage();
    }

    //AMOUNT UPDATE
    public void updatePaidAmount(double amount){
        this.paidAmount += amount;
    }

    public void updateReceivedAmount(double amount){
        this.receivedAmount += amount;
    }
}
