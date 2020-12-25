package com.example.openfinance.repository;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findAllByYear(int year);

    List<Budget> findAllByBudgetUser(Account user);

    List<Budget> findAllByBill(String bill);

    List<Budget> findAllByProgram(String program);

    List<Budget> findAllByKonto(String konto);

    List<Budget> findAllByBudgetAmountGreaterThanAndBudgetAmountLessThan(double from, double to);

    List<Budget> findAllByBudgetPlanGreaterThanAndBudgetPlanLessThan(double from, double to);

    List<Budget> findAllByBudgetRealizationGreaterThanAndBudgetRealizationLessThan(double from, double to);
}
