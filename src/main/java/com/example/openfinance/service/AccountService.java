package com.example.openfinance.service;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.exception.AccountException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public Account createAccount(Account account) throws AccountException;

    public Account getAccountById(int id);

    public List<Account> getAllAccounts();

    public void deleteAccount(int id) throws AccountException;

    public Account editAccount(int toEditId, Account newAccount) throws AccountException;

    public List<AccountTransaction> getAccountTransactions(int id);

    public Account findByName(String accountName);

    public Page<Account> getAllAccountsByPage(int page, int size);

    public List<Account> findAllByEdb(String edb);

    public List<Account> findAllByBillNumber(String billNumber);

    public void deleteAllAccounts();

    //top {number} payers
    public List<Account> findTopPayers(int number);

    //top {number} recipients
    public List<Account> findTopRecipients(int number);

}
