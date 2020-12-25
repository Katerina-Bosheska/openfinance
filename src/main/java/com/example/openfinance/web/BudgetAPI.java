package com.example.openfinance.web;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import com.example.openfinance.model.BudgetInfo;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/budget")
public class BudgetAPI {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Budget> getAllBudgetTransactions(){
        return budgetService.getAllBudgetTransactions();
    }

    @PostMapping("/create")
    public Budget addBudgetTransaction(@RequestParam int accountid,
                                       @RequestParam String bill,
                                       @RequestParam String program,
                                       @RequestParam String konto,
                                       @RequestParam int year,
                                       @RequestParam double amount,
                                       @RequestParam double plan,
                                       @RequestParam double realization){

        Account budgetUser = accountService.getAccountById(accountid);
        Budget budgetTransaction = new Budget(budgetUser, bill, program, konto, year, amount, plan, realization);
        return budgetService.createBudgetTransaction(budgetTransaction);
    }

    @GetMapping("/delete")
    public void deleteBudgetTransaction(@RequestParam int id){
        budgetService.deleteBudgetTransaction(id);
    }

    @GetMapping("/filter/year")
    public List<Budget> findByYear(@RequestParam int year){
        return budgetService.findAllByYear(year);
    }

    @GetMapping("/filter/name")
    public List<Budget> findByAccountName(@RequestParam String name){
        return budgetService.findAllByAccountName(name);
    }

    @GetMapping("/filter/bill")
    public List<Budget> findByBill(@RequestParam String bill){
        return budgetService.findAllByBill(bill);
    }

    @GetMapping("/filter/konto")
    public List<Budget> findByKonto(@RequestParam String konto){
        return budgetService.findAllByKonto(konto);
    }

    @GetMapping("/filter/program")
    public List<Budget> findByProgram(@RequestParam String program){
        return budgetService.findAllByProgram(program);
    }

    @GetMapping("/filter/amount")
    public List<Budget> findByAmount(@RequestParam double from,
                             @RequestParam double to){
        return budgetService.findAllByBudgetAmount(from, to);
    }

    @GetMapping("/filter/plan")
    public List<Budget> findByPlan(@RequestParam double from,
                           @RequestParam double to){
        return budgetService.findAllByBudgetPlan(from, to);
    }

    @GetMapping("/filter/realization")
    public List<Budget> findByRealization(@RequestParam double from,
                                  @RequestParam double to){
        return budgetService.findAllByBudgetRealization(from, to);
    }

    @GetMapping("/sum")
    public List<BudgetInfo> getBudgetInfo(@RequestParam int from,
                                    @RequestParam int to){
        return budgetService.getBudgetInfo(from, to);
    }

    @GetMapping("/sum/amount")
    public double findSumBudgetAmount(@RequestParam int year){
        return budgetService.sumBudgetAmount(year);
    }

    @GetMapping("/sum/plan")
    public double findSumBudgetPlan(@RequestParam int year){
        return budgetService.sumBudgetPlan(year);
    }

    @GetMapping("/sum/realization")
    public double findSumBudgetRealization(@RequestParam int year){
        return budgetService.sumBudgetRealization(year);
    }
}
