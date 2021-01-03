package com.example.openfinance.service;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.exception.AccountException;
import com.example.openfinance.service.exception.TransactionNotFoundException;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    AccountTransaction createTransaction(AccountTransaction transaction) throws AccountException;

    void deleteTransaction(int id) throws AccountException;

    AccountTransaction editTransaction(int toEdit, AccountTransaction transaction) throws AccountException;

    AccountTransaction getTransactionById(int id);

    List<AccountTransaction> getAllTransactions();

    //GET TRANSACTIONS
    List<AccountTransaction> findAllTransactionsByPayerName(String nameOfPayer);

    List<AccountTransaction> findAllTransactionsByRecipientName(String nameOfRecipient);

    List<AccountTransaction> getTransactionsByPayerId(int id);

    List<AccountTransaction> getTransactionsByRecipientId(int id);

    //filter by date
    List<AccountTransaction> findAllTransactionsBetweenDate(LocalDate from, LocalDate to);

    //FILTERS OF PAYERS
    List<AccountTransaction> findAllByPayerAndDate(int payerId, LocalDate date);

    List<AccountTransaction> findAllByPayerAndKonto(int payerId, String konto);

    List<AccountTransaction> findAllByPayerAndProgram(int payerId, String program);

    List<AccountTransaction> findAllByPayerAndAmount(int payerId, double from, double to);

    List<AccountTransaction> findAllByPayerAndRecipient(int payerId, String recipientName) throws AccountException, TransactionNotFoundException;

    List<AccountTransaction> findAllByPayerBill(String bill);

    List<AccountTransaction> findAllByPayerEdb(String edb);

    List<AccountTransaction> findAllByAmount(double from, double to);

    //FILTERS OF RECIPIENTS
    List<AccountTransaction> findAllByRecipientAndDate(int recipientId, LocalDate date);

    List<AccountTransaction> findAllByRecipientAndKonto(int recipientId, String konto);

    List<AccountTransaction> findAllByRecipientAndProgram(int recipientId, String program);

    List<AccountTransaction> findAllByRecipientAndAmount(int recipientId, double from, double to);

    // global search
    List<AccountTransaction> filterTransactions(String payerName, String recipientName, LocalDate from, LocalDate to);

    List<AccountTransaction> globalSearch(String value);

    //for charts needs
    List<AccountTransaction> findTopTransactions(LocalDate from, LocalDate to, int number);

    List<AccountTransaction> findPayerTransactionsBetweenDate(int payerId, LocalDate from, LocalDate to);

    List<AccountTransaction> findRecipientTransactionsBetweenDate(int recipientId, LocalDate from, LocalDate to);
}
