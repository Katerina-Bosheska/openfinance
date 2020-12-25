package com.example.openfinance.web;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/transaction")
public class TransactionAPI {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountTransaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @PostMapping("/create")
    public AccountTransaction createTransaction(@RequestParam int payerid,
                                                @RequestParam int recipientid,
                                                @RequestParam String konto,
                                                @RequestParam String program,
                                                @RequestParam double amount,
                                                @RequestParam String dateStr) throws ParseException, AccountException {

        Account payer = accountService.getAccountById(payerid);
        Account recipient = accountService.getAccountById(recipientid);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(dateStr);
        LocalDate date = new LocalDate(millis);
        AccountTransaction transaction = new AccountTransaction(payer, recipient, konto, program, amount, date);
        return transactionService.createTransaction(transaction);
    }

    @PatchMapping("/edit")
    public AccountTransaction editTransaction(@RequestParam int toedit,
                                                @RequestParam Optional<Integer> payername,
                                                @RequestParam Optional<Integer> recipientname,
                                                @RequestParam Optional<String> konto,
                                                @RequestParam Optional<String> program,
                                                @RequestParam Optional<Double> amount,
                                                @RequestParam Optional<String> dateStr) throws ParseException, AccountException {

        AccountTransaction transaction = transactionService.getTransactionById(toedit);
        Account payer = accountService.getAccountById(payername.orElseGet(() -> transaction.getPayer().getId()));
        Account recipient = accountService.getAccountById(recipientname.orElseGet(() -> transaction.getPayer().getId()));
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate date;
        if(dateStr == null){
            date = transaction.getDate();
        } else {
            long millis = formatter.parseMillis(dateStr.get());
            date = new LocalDate(millis);
        }
        AccountTransaction newTransaction = new AccountTransaction(payer, recipient, konto.orElseGet(()->transaction.getKonto()),
                program.orElseGet(()->transaction.getProgram()), amount.orElseGet(()->transaction.getAmount()), date);
        return transactionService.editTransaction(toedit, transaction);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable int id) throws AccountException {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/payer/{id}")
    public List<AccountTransaction> getTransactionsByPayerId(@PathVariable int id){
        return transactionService.getTransactionsByPayerId(id);
    }

    @GetMapping("/payer/name")
    public List<AccountTransaction> getTransactionsByPayerName(@RequestParam String name){
        return transactionService.findAllTransactionsByPayerName(name);
    }

    @GetMapping("/filter/bill")
    List<AccountTransaction> findAllByPayerBill(@RequestParam String bill){
        return transactionService.findAllByPayerBill(bill);
    }

    @GetMapping("/filter/edb")
    List<AccountTransaction> findAllByPayerEdb(@RequestParam String edb){
        return transactionService.findAllByPayerEdb(edb);
    }

    @GetMapping("/filter/amount")
    List<AccountTransaction> findAllByAmount(@RequestParam double from, double to){
        return transactionService.findAllByAmount(from, to);
    }

    //RECIPIENT

    @GetMapping("/recipient/name")
    List<AccountTransaction> findAllByRecipientName(@RequestParam String name){
        return transactionService.findAllTransactionsByRecipientName(name);
    }

    @GetMapping("/recipient/{id}")
    List<AccountTransaction> findAllByRecipientId(@PathVariable int id){
        return transactionService.getTransactionsByRecipientId(id);
    }

    // filter on main page
    @PostMapping("/filter")
    List<AccountTransaction> filterTransactions(@RequestParam String payername,
                                                @RequestParam String recipientname,
                                                @RequestParam String from,
                                                @RequestParam String to){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(from);
        LocalDate date1 = new LocalDate(millis);
        millis = formatter.parseMillis(to);
        LocalDate date2 = new LocalDate(millis);
        return transactionService.filterTransactions(payername, recipientname, date1, date2);
    }

    @GetMapping("/filter/global")
    public List<AccountTransaction> globalSearch(@RequestParam String value){
        return transactionService.globalSearch(value);
    }

    @GetMapping("/sorted")
    public List<AccountTransaction> findTopTransactions(@RequestParam String from,
                                                        @RequestParam String to,
                                                        @RequestParam int number){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(from);
        LocalDate date1 = new LocalDate(millis);
        millis = formatter.parseMillis(to);
        LocalDate date2 = new LocalDate(millis);
        return transactionService.findTopTransactions(date1, date2, number);
    }

    @GetMapping("/payer/date")
    public List<AccountTransaction> findPayerTransactionsBetweenDate(@RequestParam int id,
                                                                     @RequestParam String from,
                                                                     @RequestParam String to){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(from);
        LocalDate date1 = new LocalDate(millis);
        millis = formatter.parseMillis(to);
        LocalDate date2 = new LocalDate(millis);
        return transactionService.findPayerTransactionsBetweenDate(id, date1, date2);
    }

    @GetMapping("/recipient/date")
    public List<AccountTransaction> findRecipientTransactionsBetweenDate(@RequestParam int id,
                                                                     @RequestParam String from,
                                                                     @RequestParam String to){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(from);
        LocalDate date1 = new LocalDate(millis);
        millis = formatter.parseMillis(to);
        LocalDate date2 = new LocalDate(millis);
        return transactionService.findRecipientTransactionsBetweenDate(id, date1, date2);
    }
}
