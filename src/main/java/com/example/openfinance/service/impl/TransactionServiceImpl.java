package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    // CREATE, EDIT, DELETE, READ

    // ZA TESTIRANJE
    @Override
    public AccountTransaction createTransaction(AccountTransaction transaction) throws AccountException {
        if(transactionRepository.existsById(transaction.getId()))
            throw new AccountException("Transaction already exists!");
        double amount = transaction.getAmount();
        Account payer = transaction.getPayer();
        payer.updatePaidAmount(amount);
        Account recipient = transaction.getRecipient();
        recipient.updateReceivedAmount(amount);
        accountRepository.save(payer);
        accountRepository.save(recipient);
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(int id) throws AccountException {
        if(!transactionRepository.existsById(id))
            throw new AccountException("Transaction doesn't exist!");
        AccountTransaction transaction = this.getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public AccountTransaction editTransaction(int toEdit, AccountTransaction transaction) throws AccountException {
        if(!transactionRepository.existsById(transaction.getId()))
            throw new AccountException("Transaction doesn't exist!");
        AccountTransaction oldTransaction = this.getTransactionById(toEdit);
        transactionRepository.delete(oldTransaction);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<AccountTransaction> getAllTransactions(){
        return transactionRepository.findAll();

    }

    @Override
    public AccountTransaction getTransactionById(int id) {
        return transactionRepository.getOne(id);
    }

    // GET TRANSACTIONS
    @Override
    public List<AccountTransaction> findAllTransactionsByPayerName(String nameOfPayer) {
//        return transactionRepository.findAllByPayerName(nameOfPayer);
        List<AccountTransaction> transactions = this.getAllTransactions();
        List<AccountTransaction> result = new ArrayList<>();
        for(AccountTransaction transaction : transactions){
            if(transaction.getPayer().getName().toUpperCase().contains(nameOfPayer.toUpperCase()))
                result.add(transaction);
        }
        return result;
    }

    @Override
    public List<AccountTransaction> findAllTransactionsByRecipientName(String nameOfRecipient) {
        List<AccountTransaction> transactions = this.getAllTransactions();
        List<AccountTransaction> result = new ArrayList<>();
        for(AccountTransaction transaction : transactions){
            if(transaction.getRecipient().getName().toUpperCase().contains(nameOfRecipient.toUpperCase()))
                result.add(transaction);
        }
        return result;
    }

    @Override
    public List<AccountTransaction> getTransactionsByPayerId(int id) {
        return transactionRepository.findAllByPayerId(id);
    }

    @Override
    public List<AccountTransaction> getTransactionsByRecipientId(int id) {
        return transactionRepository.findAllByRecipientId(id);
    }

    @Override
    public List<AccountTransaction> findAllByDateBetween(LocalDate from, LocalDate to) {
        return transactionRepository.findAllByDateGreaterThanEqualAndDateLessThanEqual(from, to);
    }

    @Override
    public List<AccountTransaction> findAllByPayerAndDate(int payerId, LocalDate date) {
        Account payer = accountRepository.getOne(payerId);
        return transactionRepository.findAllByPayerAndDate(payer, date);
    }

    @Override
    public List<AccountTransaction> findAllByPayerAndKonto(int payerId, String konto) {
        Account payer = accountRepository.getOne(payerId);
        return transactionRepository.findAllByPayerAndKonto(payer, konto);
    }

    @Override
    public List<AccountTransaction> findAllByPayerAndProgram(int payerId, String program) {
        Account payer = accountRepository.getOne(payerId);
        return transactionRepository.findAllByPayerAndProgram(payer, program);
    }

    @Override
    public List<AccountTransaction> findAllByPayerAndAmount(int payerId, double from, double to) {
        Account payer = accountRepository.getOne(payerId);
        return transactionRepository.findAllByPayerAndAmountGreaterThanAndAmountLessThan(payer, from, to);
    }

    // ZA TESTIRANJE
    @Override
    public List<AccountTransaction> findAllByPayerAndRecipient(int payerId, String recipientName) {
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> result =  new ArrayList<>();
        for(AccountTransaction t : transactions){
            if(t.getPayer().getId() == payerId){
                if(t.getRecipient().getName().toUpperCase().contains(recipientName.toUpperCase()))
                    result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<AccountTransaction> findAllByPayerBill(String bill) {
        return transactionRepository.findAllByPayerBill(bill);
    }

    @Override
    public List<AccountTransaction> findAllByPayerEdb(String edb) {
        return transactionRepository.findAllByPayerEdb(edb);
    }

    @Override
    public List<AccountTransaction> findAllByAmount(double from, double to) {
        return transactionRepository.findAllByAmountGreaterThanAndAmountLessThan(from, to);
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndDate(int recipientId, LocalDate date) {
        return null;
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndKonto(int recipientId, String konto) {
        return null;
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndProgram(int recipientId, String program) {
        return null;
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndAmount(int recipientId, double from, double to) {
        return null;
    }

    // ZA TESTIRANJE
    @Override
    public List<AccountTransaction> filterTransactions(String payerName, String recipientName, LocalDate from, LocalDate to) {
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> filtered = new ArrayList<>();
        for(AccountTransaction t : transactions){
            if(t.getPayer().getName().toUpperCase().contains(payerName.toUpperCase()) && t.getRecipient().getName().toUpperCase().contains(recipientName.toUpperCase()))
                filtered.add(t);
        }
        // smeni go so kodche
        List<AccountTransaction> betweenDates = this.findAllByDateBetween(from, to);
        List<AccountTransaction> result = new ArrayList<>();
        for(AccountTransaction t : filtered){
            if(betweenDates.contains(t))
                result.add(t);
        }
        return result;
    }

    @Override
    public List<AccountTransaction> globalSearch(String value){
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> filtered = new ArrayList<>();
        value = value.toUpperCase();
        for(AccountTransaction t : transactions){
            if(t.getRecipient().getName().toUpperCase().contains(value) || t.getPayer().getName().toUpperCase().contains(value) ||
                t.getRecipient().getBillNumber().equals(value) || t.getPayer().getBillNumber().equals(value) ||
                t.getRecipient().getEDB().equals(value) || t.getPayer().getEDB().equals(value) ||
                t.getKonto().equals(value) || t.getProgram().equals(value)){
                filtered.add(t);
            }
        }
        return filtered;
    }

    // MOZHDA ZA TESTIRANJE
    @Override
    public List<AccountTransaction> findTopTransactions(LocalDate from, LocalDate to, int number) {
        List<AccountTransaction> topTransactions = transactionRepository.findAllByDateGreaterThanEqualAndDateLessThanEqualOrderByAmountDesc(from, to);
        if(topTransactions.size() < number){
            return topTransactions;
        }
        return topTransactions.subList(0, number);
    }

    @Override
    public List<AccountTransaction> findPayerTransactionsBetweenDate(int payerId, LocalDate from, LocalDate to) {
        Account account = accountRepository.getOne(payerId);
        return transactionRepository.findAllByPayerAndDateGreaterThanEqualAndDateLessThanEqualOrderByDateAsc(account, from, to);
    }

    @Override
    public List<AccountTransaction> findRecipientTransactionsBetweenDate(int recipientId, LocalDate from, LocalDate to) {
        Account account = accountRepository.getOne(recipientId);
        return transactionRepository.findAllByRecipientAndDateGreaterThanEqualAndDateLessThanEqualOrderByDateAsc(account, from, to);
    }


}
