package com.example.openfinance.model;

import com.sun.istack.NotNull;

import javax.persistence.*;


@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account budgetUser;

    @NotNull
    @Column(columnDefinition="nvarchar(255)")
    private String bill;

    @Column(columnDefinition="nvarchar(255)")
    private String program;

    @Column(columnDefinition="nvarchar(255)")
    private String konto;

    @NotNull
    private int year;

    @NotNull
    private double budgetAmount;

    @NotNull
    private double budgetPlan;

    @NotNull
    private double budgetRealization;

    public Budget(){}

    public Budget(Account budgetUser, String bill, String program, String konto, int year, double budgetAmount, double budgetPlan, double budgetRealization) {
        this.budgetUser = budgetUser;
        this.bill = bill;
        this.program = program;
        this.konto = konto;
        this.year = year;
        this.budgetAmount = budgetAmount;
        this.budgetPlan = budgetPlan;
        this.budgetRealization = budgetRealization;
    }

    public void apply(Budget budget){
        this.budgetUser = budget.getBudgetUser();
        this.bill = budget.getBill();
        this.program = budget.getProgram();
        this.konto = budget.getKonto();
        this.year = budget.getYear();
        this.budgetAmount = budget.getBudgetAmount();
        this.budgetPlan = budget.getBudgetPlan();
        this.budgetRealization = budget.getBudgetRealization();
    }

    //GETTERS

    public int getId() {
        return Id;
    }

    public Account getBudgetUser() {
        return budgetUser;
    }

    public String getBill() {
        return bill;
    }

    public String getProgram() {
        return program;
    }

    public String getKonto() {
        return konto;
    }

    public int getYear() {
        return year;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public double getBudgetPlan() {
        return budgetPlan;
    }

    public double getBudgetRealization() {
        return budgetRealization;
    }

    //SETTERS

    public void setId(int id) {
        Id = id;
    }

    public void setBudgetUser(Account budgetUser) {
        this.budgetUser = budgetUser;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setBudgetPlan(double budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public void setBudgetRealization(double budgetRealization) {
        this.budgetRealization = budgetRealization;
    }
}