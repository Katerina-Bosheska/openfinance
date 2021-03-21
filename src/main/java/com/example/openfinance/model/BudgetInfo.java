package com.example.openfinance.model;

import lombok.Getter;

@Getter
public class BudgetInfo {

    private int year;
    private double amount;
    private double plan;
    private double realization;

    public BudgetInfo(int year, double amount, double plan, double realization) {
        this.year = year;
        this.amount = amount;
        this.plan = plan;
        this.realization = realization;
    }

}
