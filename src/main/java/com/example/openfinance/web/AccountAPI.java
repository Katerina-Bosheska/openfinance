package com.example.openfinance.web;

import com.example.openfinance.model.Account;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/account")
public class AccountAPI {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/get")
    public Account getByID(@RequestParam int id){
        return accountService.getAccountById(id);
    }

    @PostMapping("/create")
    public Account createAccount(@RequestParam String name,
                                 @RequestParam String edb,
                                 @RequestParam String bill,
                                 @RequestParam(required = false) Optional<String> address,
                                 @RequestParam int type,
                                 @RequestParam(required = false) Optional<Double> paidAmount,
                                 @RequestParam(required = false) Optional<Double> receivedAmount) throws AccountException {

        double paid = paidAmount.orElseGet(() -> 0.0);
        double received = receivedAmount.orElseGet(() -> 0.0);
        Account account = new Account(name, edb, bill, address.orElseGet(() -> ""), type, paid, received);
        return accountService.createAccount(account);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable int id) throws AccountException {
        accountService.deleteAccount(id);
    }

    @PatchMapping("/edit/{id}")
    public Account editAccount(@PathVariable int id,
                               @RequestParam(required = false) Optional<String> name,
                               @RequestParam(required = false) Optional<String> edb,
                               @RequestParam(required = false) Optional<String> bill,
                               @RequestParam(required = false) Optional<String> address,
                               @RequestParam(required = false) Optional<Integer> type,
                               @RequestParam(required = false) Optional<Double> paidAmount,
                               @RequestParam(required = false) Optional<Double> receivedAmount) throws AccountException {

        Account oldAccount = accountService.getAccountById(id);
        String accName = name.orElseGet(() -> oldAccount.getName());
        String accEdb = edb.orElseGet(() -> oldAccount.getEDB());
        String accBill = bill.orElseGet(() -> oldAccount.getBillNumber());
        String accAddr = address.orElseGet(() -> oldAccount.getAddress());
        int accType = type.orElseGet(() -> oldAccount.getType());
        double paid = paidAmount.orElseGet(() -> oldAccount.getPaidAmount());
        double received = receivedAmount.orElseGet(() -> oldAccount.getReceivedAmount());
        Account account = new Account(accName, accEdb, accBill, accAddr, accType, paid, received);
        return accountService.editAccount(id, account);
    }

    @GetMapping("/edb/{edb}")
    public List<Account> getAccountsByEdb(@PathVariable String edb){
        return accountService.findAllByEdb(edb);
    }

    @GetMapping("/bill/{bill}")
    public List<Account> getAccountsByBillNumber(@PathVariable String bill){
        return accountService.findAllByBillNumber(bill);
    }

    @GetMapping("/name")
    public Account getAccountByName(@RequestParam(required=false, name="name") String name){
        return accountService.findByName(name);
    }

    @GetMapping("/delete/all")
    void deleteAllAccounts(){
        accountService.deleteAllAccounts();
    }

    @GetMapping("/payers/ordered")
    public List<Account> findTopPayers(@RequestParam int number){
        return accountService.findTopPayers(number);
    }

    @GetMapping("/recipients/ordered")
    public List<Account> findTopRecipients(@RequestParam int number){
        return accountService.findTopRecipients(number);
    }

}
