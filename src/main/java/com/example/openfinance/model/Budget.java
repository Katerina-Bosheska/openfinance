package com.example.openfinance.model;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Budget))
            return false;
        Budget other = (Budget) o;
        return this.Id == other.getId();
    }

    public void setId(int id){
        this.Id = id;
    }

}