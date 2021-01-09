package com.example.openfinance;


import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.model.Budget;
import com.example.openfinance.repository.BudgetRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.BudgetService;
import com.example.openfinance.service.FilterRecipientService;
import com.example.openfinance.service.impl.BudgetServiceImpl;
import com.example.openfinance.service.impl.FilterRecipientServiceImpl;
import com.example.openfinance.service.impl.TransactionServiceImpl;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class FilterRecipientTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    @Autowired
    private FilterRecipientServiceImpl transactionService;

    LocalDate createLocalDate(String strDate){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(strDate);
        return new LocalDate(millis);
    }

    @Test
    public void testFindByRecipientAndPayer(){

        Account a1 = new Account("ФОНД ЗА ЗДРАВСТВО", "4030991261703", "660026001166012", "БУЛ. МАКЕДОНИЈА ББ", 1, 0.0, 0.0);
        Account a2 = new Account("МИНИСТЕРСТВО ЗА ТРУД И СОЦИЈАЛНА ПОЛИТИКА", "4030990252999", "150010029963718", "ДАМЕ ГРУЕВ 14", 1, 0.0, 0.0);
        Account a3 = new Account("ФОНД НА ПЕНЗИСКО И ИНВАЛИДСКО ОСИГУРУВАЊЕ", "4030992242699", "660046003866012", "ВЛАДИМИР КОМАРОВ БР.11 СКОПЈЕ", 1, 0.0, 0.0);
        Account a4 = new Account("УПРАВА ЗА ЈАВНИ ПРИХОДИ", "4030000404218", "090051567963715", "БУЛ. КУЗМАН ЈОСИФОСКИ-ПИТУ БР.1", 1, 0.0, 0.0);
        a1.setId(1);
        a2.setId(2);
        a3.setId(3);
        a4.setId(4);

        LocalDate date = this.createLocalDate("2021-01-10");

        AccountTransaction t1 = new AccountTransaction(a1, a2, "konto1", "program1", 3400, date);
        AccountTransaction t2 = new AccountTransaction(a1, a2, "konto2", "program2", 5000, date);
        AccountTransaction t3 = new AccountTransaction(a1, a2, "konto3", "program3", 1200, date);
        AccountTransaction t4 = new AccountTransaction(a3, a2, "konto4", "program4", 2000, date);
        AccountTransaction t5 = new AccountTransaction(a4, a2, "konto5", "program4", 2000, date);
        AccountTransaction t6 = new AccountTransaction(a4, a1, "konto6", "program4", 2000, date);
        AccountTransaction t7 = new AccountTransaction(a4, a3, "konto6", "program4", 2000, date);
        AccountTransaction t8 = new AccountTransaction(a1, a1, "konto6", "program4", 2000, date);

        List<AccountTransaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);
        transactions.add(t4);
        transactions.add(t5);
        transactions.add(t6);
        transactions.add(t7);
        transactions.add(t8);

        List<AccountTransaction> expectedResult = new ArrayList<>();
        expectedResult.add(t1);
        expectedResult.add(t2);
        expectedResult.add(t3);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        List<AccountTransaction> result = transactionService.findAllByRecipientAndPayerName(2, "здравство");

        Assertions.assertEquals(result, expectedResult);

    }


}
