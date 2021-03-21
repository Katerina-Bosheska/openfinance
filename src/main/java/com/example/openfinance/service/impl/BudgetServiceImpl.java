package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import com.example.openfinance.model.BudgetInfo;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.BudgetRepository;
import com.example.openfinance.service.BudgetService;
import com.example.openfinance.service.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findAccount(int accountId){
        return accountRepository.getOne(accountId);
    }

    @Override
    public Budget createBudgetTransaction(Budget budgetTransaction) {
        return budgetRepository.save(budgetTransaction);
    }

    @Override
    public boolean transactionExistsById(int id){
        return budgetRepository.existsById(id);
    }

    @Override
    public void deleteBudgetTransaction(int id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public Budget getBudgetTransaction(int id) {
        return budgetRepository.getOne(id);
    }

    @Override
    public List<Budget> getAllBudgetTransactions() throws TransactionNotFoundException {
        List<Budget> budgetTransactions = budgetRepository.findAll();
        if(budgetTransactions.size() == 0)
            throw new TransactionNotFoundException("No transactions were found.");
        return budgetTransactions;
    }

    @Override
    public List<Budget> findAllByYear(int year) {
        return budgetRepository.findAllByYear(year);
    }

    @Override
    public List<Budget> findAllByBudgetUser(Account user) {
        return budgetRepository.findAllByBudgetUser(user);
    }

    @Override
    public List<Budget> findAllByAccountName(String name){
        List<Budget> budgetTransactions = budgetRepository.findAll();
        List<Budget> result = new ArrayList<>();
        for(int i=0; i<budgetTransactions.size(); i++){
            Budget transaction = budgetTransactions.get(i);
            if(transaction.getBudgetUser().getName().contains(name.toUpperCase())){
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Budget> findAllByBill(String bill) {
        List<Budget> budgetTransactions = budgetRepository.findAll();
        List<Budget> result = new ArrayList<>();
        for(int i=0; i<budgetTransactions.size(); i++){
            Budget transaction = budgetTransactions.get(i);
            if(transaction.getBill().toUpperCase().contains(bill.toUpperCase())){
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Budget> findAllByProgram(String program) {
        List<Budget> budgetTransactions = budgetRepository.findAll();
        List<Budget> result = new ArrayList<>();
        for(int i=0; i<budgetTransactions.size(); i++){
            Budget transaction = budgetTransactions.get(i);
            if(transaction.getProgram().toUpperCase().contains(program.toUpperCase())){
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Budget> findAllByKonto(String konto) {
        List<Budget> budgetTransactions = budgetRepository.findAll();
        List<Budget> result = new ArrayList<>();
        for(int i=0; i<budgetTransactions.size(); i++){
            Budget transaction = budgetTransactions.get(i);
            if(transaction.getKonto().toUpperCase().contains(konto.toUpperCase())){
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Budget> findAllByBudgetAmount(double from, double to) {
        return budgetRepository.findAllByBudgetAmountGreaterThanAndBudgetAmountLessThan(from, to);
    }

    @Override
    public List<Budget> findAllByBudgetPlan(double from, double to) {
        return budgetRepository.findAllByBudgetPlanGreaterThanAndBudgetPlanLessThan(from, to);
    }

    @Override
    public List<Budget> findAllByBudgetRealization(double from, double to) {
        return budgetRepository.findAllByBudgetRealizationGreaterThanAndBudgetRealizationLessThan(from, to);
    }

    @Override
    public List<BudgetInfo> getBudgetInfo(int from, int to){
        List<BudgetInfo> result = new ArrayList<>();
        for(int i=from; i<to; i++){
            int year = i;
            List<Budget> transactions = budgetRepository.findAllByYear(year);
            double sumAmount = 0.0;
            double sumPlan = 0.0;
            double sumRealization = 0.0;
            for(int j=0; j<transactions.size(); j++){
                sumAmount += transactions.get(j).getBudgetAmount();
                sumPlan += transactions.get(j).getBudgetPlan();
                sumRealization += transactions.get(j).getBudgetRealization();
            }
            result.add(new BudgetInfo(year, sumAmount, sumPlan, sumRealization));
        }
        return result;
    }

    @Override
    public double sumBudgetAmount(int year) {
        List<Budget> transactions = budgetRepository.findAllByYear(year);
        double sum = 0.0;
        for(int i=0; i<transactions.size(); i++){
            sum += transactions.get(i).getBudgetAmount();
        }
        return sum;
    }

    @Override
    public double sumBudgetPlan(int year) {
        List<Budget> transactions = budgetRepository.findAllByYear(year);
        double sum = 0.0;
        for(int i=0; i<transactions.size(); i++){
            sum += transactions.get(i).getBudgetPlan();
        }
        return sum;
    }

    @Override
    public double sumBudgetRealization(int year) {
        List<Budget> transactions = budgetRepository.findAllByYear(year);
        double sum = 0.0;
        for(int i=0; i<transactions.size(); i++){
            sum += transactions.get(i).getBudgetRealization();
        }
        return sum;
    }
}
