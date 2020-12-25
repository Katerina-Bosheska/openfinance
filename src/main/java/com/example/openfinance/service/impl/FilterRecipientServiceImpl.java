package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.FilterRecipientService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterRecipientServiceImpl implements FilterRecipientService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountTransaction> findAllByRecipientAndDate(int recipientid, LocalDate date) {
        Account recipient = accountRepository.getOne(recipientid);
        return transactionRepository.findAllByRecipientAndDate(recipient, date);
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndKonto(int recipientid, String konto) {
        Account recipient = accountRepository.getOne(recipientid);
        return transactionRepository.findAllByRecipientAndKonto(recipient, konto);
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndProgram(int recipientid, String program) {
        Account recipient = accountRepository.getOne(recipientid);
        return transactionRepository.findAllByRecipientAndProgram(recipient, program);
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndBill(int recipientid, String bill) {
        Account recipient = accountRepository.getOne(recipientid);
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> result = new ArrayList<>();
        for(AccountTransaction t : transactions){
            if(t.getRecipient().getId() == recipientid){
                if(t.getPayer().getBillNumber().equals(bill))
                    result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndPayerName(int recipientid, String payerName) {
        List<AccountTransaction> transactions = transactionRepository.findAll();
        List<AccountTransaction> result = new ArrayList<>();
        for(AccountTransaction t : transactions){
            if(t.getRecipient().getId() == recipientid){
                if(t.getPayer().getName().toUpperCase().contains(payerName.toUpperCase()))
                    result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<AccountTransaction> findAllByRecipientAndAmountGreaterThanAndAmountLessThan(int recipientid, double from, double to) {
        Account recipient = accountRepository.getOne(recipientid);
        return transactionRepository.findAllByRecipientAndAmountGreaterThanAndAmountLessThan(recipient, from, to);
    }


}
