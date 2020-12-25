package com.example.openfinance.model;

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

    public int getYear() {
        return year;
    }

    public double getAmount() {
        return amount;
    }

    public double getPlan() {
        return plan;
    }

    public double getRealization() {
        return realization;
    }
}
