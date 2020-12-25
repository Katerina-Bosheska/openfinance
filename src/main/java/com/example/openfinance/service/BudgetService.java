package com.example.openfinance.service;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import com.example.openfinance.model.BudgetInfo;

import java.util.List;

public interface BudgetService {

    Budget createBudgetTransaction(Budget budgetTransaction);

    void deleteBudgetTransaction(int id);

    Budget getBudgetTransaction(int id);

    List<Budget> getAllBudgetTransactions();

    // FILTERING
    List<Budget> findAllByYear(int year);

    List<Budget> findAllByBudgetUser(Account user);

    List<Budget> findAllByAccountName(String name);

    List<Budget> findAllByBill(String bill);

    List<Budget> findAllByProgram(String program);

    List<Budget> findAllByKonto(String konto);

    List<Budget> findAllByBudgetAmount(double from, double to);

    List<Budget> findAllByBudgetPlan(double from, double to);

    List<Budget> findAllByBudgetRealization(double from, double to);

    List<BudgetInfo> getBudgetInfo(int from, int to);

    double sumBudgetAmount(int year);

    double sumBudgetPlan(int year);

    double sumBudgetRealization(int year);
}
