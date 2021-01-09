package com.example.openfinance.repository;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Integer> {

    @Query("SELECT t FROM AccountTransaction t JOIN t.payer p WHERE p.name LIKE ?1")
    List<AccountTransaction> findAllByPayerName(String name);

    @Query("SELECT t FROM AccountTransaction t JOIN t.payer p WHERE p.id = ?1")
    List<AccountTransaction> findAllByPayerId(int id);

    @Query("SELECT t FROM AccountTransaction t JOIN t.payer p WHERE p.billNumber = ?1")
    List<AccountTransaction> findAllByPayerBill(String bill);

    @Query("SELECT t FROM AccountTransaction t JOIN t.payer p WHERE p.EDB = ?1")
    List<AccountTransaction> findAllByPayerEdb(String edb);

    @Query("SELECT t FROM AccountTransaction t JOIN t.recipient r WHERE r.name = ?1")
    List<AccountTransaction> findAllByRecipientName(String name);

    @Query("SELECT t FROM AccountTransaction t JOIN t.recipient r WHERE r.id = ?1")
    List<AccountTransaction> findAllByRecipientId(int id);

    List<AccountTransaction> findAllByAmount(double amount);

    List<AccountTransaction> findAllByAmountGreaterThanAndAmountLessThan(double from, double to);

    //filters for payers
    List<AccountTransaction> findAllByPayerAndDate(Account payer, LocalDate date);

    List<AccountTransaction> findAllByPayerAndKonto(Account payer, String konto);

    List<AccountTransaction> findAllByPayerAndProgram(Account payer, String program);

    List<AccountTransaction> findAllByPayerAndAmountGreaterThanAndAmountLessThan(Account payer, double from, double to);

    List<AccountTransaction> findAllByDateBetween(Date from, Date to);

    //filters for recipients
    List<AccountTransaction> findAllByRecipientAndDate(Account recipient, LocalDate date);

    List<AccountTransaction> findAllByRecipientAndKonto(Account recipient, String konto);

    List<AccountTransaction> findAllByRecipientAndProgram(Account recipient, String program);

    List<AccountTransaction> findAllByRecipientAndPayerName(Account recipient, String payerName);

    List<AccountTransaction> findAllByRecipientAndAmountGreaterThanAndAmountLessThan(Account payer, double from, double to);

    // filter by date
    List<AccountTransaction> findAllByDateAfterAndDateBefore(LocalDate from, LocalDate to);

    List<AccountTransaction> findAllByRecipientAndDateAfterAndDateBefore(Account recipient, LocalDate from, LocalDate to);

    List<AccountTransaction> findAllByDateGreaterThanEqualAndDateLessThanEqual(LocalDate from, LocalDate to);

    // ordered
    List<AccountTransaction> findAllByDateGreaterThanEqualAndDateLessThanEqualOrderByAmountDesc(LocalDate from, LocalDate to);

    //find transactions of account between dates
    List<AccountTransaction> findAllByPayerAndDateGreaterThanEqualAndDateLessThanEqualOrderByDateAsc(Account payer, LocalDate from, LocalDate to);

    List<AccountTransaction> findAllByRecipientAndDateGreaterThanEqualAndDateLessThanEqualOrderByDateAsc(Account recipient, LocalDate from, LocalDate to);
}
