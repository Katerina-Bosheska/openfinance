package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if(payerId == 0)
            throw new IllegalArgumentException();
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
        if(payerName.equals("") && recipientName.equals("") && from == null && to == null)
            return this.getAllTransactions();
        else {
            if(from == null){
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                long millis = formatter.parseMillis("2010-01-01");
                from = new LocalDate(millis);
            }
            if(to == null) to = new LocalDate();
            if(payerName == null) payerName = "";
            if(recipientName == null) recipientName = "";
        }
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> filtered = new ArrayList<>();
        for(AccountTransaction t : transactions){
            String tPayer = t.getPayer().getName().toLowerCase();
            String tRecipient = t.getRecipient().getName().toLowerCase();
            LocalDate tDate = t.getDate();
            if(tPayer.contains(payerName.toLowerCase()) &&
                    tRecipient.contains(recipientName.toLowerCase())){
                if(tDate.isAfter(from) && tDate.isBefore(to)){
                    filtered.add(t);
                }
            }

        }
        return filtered;
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

    //ZA TESTIRANJE
    @Override
    public List<AccountTransaction> findTopTransactions(LocalDate from, LocalDate to, int number) {
        if(to.isBefore(from))
            throw new IllegalArgumentException();
        if(from == null || to == null)
            throw new IllegalArgumentException();
        List<AccountTransaction> topTransactions = this.findAllTransactionsBetweenDate(from, to);
        if(topTransactions.size() < number){
            return topTransactions;
        }
        return topTransactions.subList(0, number);
    }

    @Override
    public List<AccountTransaction> findAllTransactionsBetweenDate(LocalDate from, LocalDate to){
        return transactionRepository.findAllByDateGreaterThanEqualAndDateLessThanEqualOrderByAmountDesc(from, to);
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
