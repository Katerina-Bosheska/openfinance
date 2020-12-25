package com.example.openfinance.service;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface StatisticsService {

    public int getNumberOfPublicInstitutions();

    public int getNumberOfComapanies();

    public int getNumberOfIndividuals();

    public int getNumberOfTransactions();

    List<AccountTransaction> topPayersOfMonth() throws ParseException;

    List<Account> topRecipientsOfMonth(int month);

}
