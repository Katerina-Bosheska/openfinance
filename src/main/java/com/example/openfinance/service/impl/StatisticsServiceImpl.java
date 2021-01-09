package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.StatisticsService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public int getNumberOfPublicInstitutions() {
        return accountRepository.findAllByType(1).size();
    }

    @Override
    public int getNumberOfComapanies() {
        return accountRepository.findAllByType(2).size();
    }

    @Override
    public int getNumberOfIndividuals() {
        return accountRepository.findAllByType(3).size();
    }

    @Override
    public int getNumberOfTransactions() {
        return transactionRepository.findAll().size();
    }

    @Override
    public List<AccountTransaction> topPayersOfMonth() throws ParseException {
        return allTransactionsOfMonth();
    }

    public double avgTransactionAmount(LocalDate from, LocalDate to){
        List<AccountTransaction> transactions = transactionRepository.findAllByDateAfterAndDateBefore(from, to);
        double sum = 0.0;
        for(AccountTransaction t : transactions){
            double amount = t.getAmount();
            sum += amount;
        }
        return sum/transactions.size();
    }

    public Account findMaxRecipient(LocalDate from, LocalDate to){
        List<Account> accounts = accountRepository.findAll();
        double maxAmount = 0;
        Account maxRecipient = null;
        for(Account a : accounts){
            List<AccountTransaction> transactionsByAccount = transactionRepository.findAllByRecipientAndDateAfterAndDateBefore(a, from, to);
            double totalAmountByAccount = 0;
            for(AccountTransaction t : transactionsByAccount){
                totalAmountByAccount += t.getAmount();
            }
            if(totalAmountByAccount > maxAmount){
                maxAmount = totalAmountByAccount;
                maxRecipient = a;
            }
        }
        return maxRecipient;
    }

    @Override
    public List<Account> topRecipientsOfMonth(int month) {
        return null;
    }

    public List<AccountTransaction> allTransactionsOfMonth() throws ParseException {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentDate);
        String[] partsOfDate = date.split("-");
        String year = partsOfDate[0];
        String month = partsOfDate[1];
        String fromDateStr = year + "-" + month + "-" + "01";
        String toDateStr;
        if(month.equals("12")){
            toDateStr = (Integer.parseInt(year) + 1) + "-" + "01-01";
        } else if(month.equals("11") || month.equals("10") || month.equals("09")){
            toDateStr = year + "-" + (Integer.parseInt(month) + 1) + "-01";
        }
        else toDateStr = year + "-0" + (Integer.parseInt(month) + 1) + "-01";
        Date fromDate = formatter.parse(fromDateStr);
        Date toDate = formatter.parse(toDateStr);
        return transactionRepository.findAllByDateBetween(fromDate, toDate);
    }

}
