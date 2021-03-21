package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountById(int id) {
        return accountRepository.getOne(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(Account account) throws AccountException {
        if(accountRepository.existsById(account.getId())){
            throw new AccountException("Account already exists!");
        }
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(int id) throws AccountException {
        if(!accountRepository.existsById(id))
            throw new AccountException("Account doesn't exist");
        Account account = this.getAccountById(id);
        accountRepository.delete(account);
    }

    @Override
    public Account editAccount(int toEditId, Account newAccount) throws AccountException {
        if(newAccount == null)
            throw new AccountException("You can't insert a null object");
        if(!accountRepository.existsById(toEditId))
            throw new AccountException("Account doesn't exist.");
        Account accountToEdit = this.getAccountById(toEditId);
        accountToEdit.applyAccount(newAccount);
        return accountRepository.save(accountToEdit);
    }

    @Override
    public List<AccountTransaction> getAccountTransactions(int id) {
        Account account = this.getAccountById(id);
        return account.getTransactions();
    }

    //FILTER METHODS

    @Override
    public List<Account> findAllByEdb(String edb) {
        return accountRepository.findAllByEDB(edb);
    }

    @Override
    public List<Account> findAllByBillNumber(String billNumber) {
        return accountRepository.findAllByBillNumber(billNumber);
    }

    @Override
    public Account findByName(String accountName){
        return accountRepository.findByName(accountName);
    }

    @Override
    public Page<Account> getAllAccountsByPage(int page, int size) {
        Page<Account> result = this.accountRepository.findAll(PageRequest.of(page, size));
        return result;
    }

    @Override
    public void deleteAllAccounts(){
        this.accountRepository.deleteAll();
    }

    @Override
    public List<Account> findTopPayers(int number) {
        List<Account> topPayers = accountRepository.findAllByOrderByPaidAmountDesc();
        if(topPayers.size() < number){
            return topPayers;
        }
        return topPayers.subList(0, number);
    }

    @Override
    public List<Account> findTopRecipients(int number) {
        List<Account> topRecipients = accountRepository.findAllByOrderByReceivedAmountDesc();
        if(topRecipients.size() < number){
            return topRecipients;
        }
        return topRecipients.subList(0, number);
    }

}
