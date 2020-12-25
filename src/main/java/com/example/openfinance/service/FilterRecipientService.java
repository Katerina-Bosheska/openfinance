package com.example.openfinance.service;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FilterRecipientService {

    List<AccountTransaction> findAllByRecipientAndDate(int recipientid, LocalDate date);

    List<AccountTransaction> findAllByRecipientAndKonto(int recipientid, String konto);

    List<AccountTransaction> findAllByRecipientAndProgram(int recipientid, String program);

    List<AccountTransaction> findAllByRecipientAndBill(int recipientid, String bill);

    List<AccountTransaction> findAllByRecipientAndPayerName(int recipientid, String payerName);

    List<AccountTransaction> findAllByRecipientAndAmountGreaterThanAndAmountLessThan(int recipientid, double from, double to);
}
