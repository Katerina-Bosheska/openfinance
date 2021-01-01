package com.example.openfinance;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import jdk.vm.ci.meta.Local;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

@SpringBootTest
public class TransactionTests {

    @Autowired
    private TransactionService transactionService;


    LocalDate createLocalDate(String strDate){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(strDate);
        return new LocalDate(millis);
    }

    boolean checkIfDateBetween(LocalDate date, LocalDate from, LocalDate to){
        return date.isBefore(to) && date.isAfter(from);
    }


    // TESTING findTopTransactions

    // every parameter is valid, should return correct number of transactions
    @Test
    void testFindTopT1() {
        LocalDate from = this.createLocalDate("2020-04-01");
        LocalDate to = this.createLocalDate("2020-07-01");
        List<AccountTransaction> topTransactions = transactionService.findTopTransactions(from, to, 5);
        Assertions.assertEquals(topTransactions.size(), 5);
        for(AccountTransaction t : topTransactions)
            Assertions.assertTrue(this.checkIfDateBetween(t.getDate(), from, to));
    }

    // every parameter is valid, number is bigger than total number of transactions in db
    // should return the total number of transactions
    @Test
    void testFindTopT2() {
        LocalDate from = this.createLocalDate("2020-04-01");
        LocalDate to = this.createLocalDate("2020-07-01");
        int totalNumOfTransactions = transactionService.findAllTransactionsBetweenDate(from, to).size();
        List<AccountTransaction> topTransactions = transactionService.findTopTransactions(from, to, 5);
        Assertions.assertEquals(topTransactions.size(), totalNumOfTransactions);
        for(AccountTransaction t : topTransactions)
            Assertions.assertTrue(this.checkIfDateBetween(t.getDate(), from, to));
    }

    // every parameter is valid, from and to are equal
    @Test
    void testFindTopT3() {
        LocalDate from = this.createLocalDate("2020-04-01");
        LocalDate to = this.createLocalDate("2020-04-01");
        int totalNumOfTransactions = transactionService.findAllTransactionsBetweenDate(from, to).size();
        List<AccountTransaction> topTransactions = transactionService.findTopTransactions(from, to, 5);
        if(totalNumOfTransactions < 5)
            Assertions.assertEquals(topTransactions.size(), totalNumOfTransactions);
        else Assertions.assertEquals(topTransactions.size(), 5);
        for(AccountTransaction t : topTransactions)
            Assertions.assertTrue(this.checkIfDateBetween(t.getDate(), from, to));
    }

    // from is after to, we expect the function to throw an exception
    @Test
    void testFindTopT4() throws IllegalArgumentException {
        LocalDate from = this.createLocalDate("2020-05-01");
        LocalDate to = this.createLocalDate("2020-04-01");
        Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.findTopTransactions(from, to, 5));
    }

    // number is zero, we expect an empty list
    @Test
    void testFindTopT5() {
        LocalDate from = this.createLocalDate("2020-04-01");
        LocalDate to = this.createLocalDate("2020-07-01");
        Assertions.assertEquals(transactionService.findTopTransactions(from, to, 0).size(), 0);
    }

    @Test
    void testFindTopT6() throws IllegalArgumentException{
        LocalDate from = this.createLocalDate("2020-04-01");
        LocalDate to = this.createLocalDate("2020-07-01");
        Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.findTopTransactions(from, to, 5));
    }

    // to is null
    @Test
    void testFindTopT7() throws IllegalArgumentException{
        LocalDate from = this.createLocalDate("2020-04-01");
        Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.findTopTransactions(from, null, 5));
    }

    // from is null
    @Test
    void testFindTopT8() throws IllegalArgumentException{
        LocalDate to = this.createLocalDate("2020-07-01");
        Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.findTopTransactions(null, to, 5));
    }

}
