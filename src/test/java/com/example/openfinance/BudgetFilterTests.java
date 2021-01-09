package com.example.openfinance;


import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import com.example.openfinance.repository.BudgetRepository;
import com.example.openfinance.service.BudgetService;
import com.example.openfinance.service.impl.BudgetServiceImpl;
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
public class BudgetFilterTests {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    @Autowired
    private BudgetServiceImpl budgetService;

    @Test
    public void testFindByYear(){
        List<Budget> budgetList = new ArrayList<>();

        Account a1 = new Account("Министерство за здравство", "25252525");
        Account a2 = new Account("Министерство за финансии", "25252525");
        Account a3 = new Account("Фонд за пензиско осигурување", "41287987");
        Account a4 = new Account("Фонд за труд и социјала", "872312311");

        Budget b1 = new Budget(a1, "Основен буџет", "Здравствена дејност и осигурување", "Блок дотации", 2018, 16140905, 534300, 5345700);
        Budget b2 = new Budget(a2, "Основен буџет", "Пренесување на надлежности на ЕЛС", "Трансфери", 2018, 16140905, 534300, 5345700);
        Budget b3 = new Budget(a3, "Буџет за фондови", "Пензиско и инавлидско осигурување", "Трансфери до Фондот за ПИОМ", 2017, 16140905, 534300, 53400);
        Budget b4 = new Budget(a4, "Буџет за фондови", "Осигурување", "Блок дотации", 2016, 16140905, 534300, 5345700);

        b1.setId(1);
        b2.setId(2);
        b3.setId(3);
        b4.setId(4);

        budgetList.add(b1);
        budgetList.add(b2);
        budgetList.add(b3);
        budgetList.add(b4);

        Mockito.when(budgetRepository.findAll()).thenReturn(budgetList);

        List<Budget> expectedList = new ArrayList<>();
        expectedList.add(b1);
        expectedList.add(b2);

        List<Budget> result = budgetService.findAllByBill("Основен буџет");

        Assertions.assertEquals(result, expectedList);
    }


}
