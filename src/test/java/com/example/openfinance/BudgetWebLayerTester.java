package com.example.openfinance;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.Budget;
import com.example.openfinance.service.BudgetService;
import com.example.openfinance.service.exception.AccountException;
import com.example.openfinance.service.exception.TransactionNotFoundException;
import com.example.openfinance.web.BudgetAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.NestedCheckedException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetAPI.class)
@ActiveProfiles("test")
@ContextConfiguration
public class BudgetWebLayerTester {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BudgetService budgetService;

    @Test
    public void getAllBudgetTransactionsReturnsOk() throws Exception {

        List<Budget> budgetList = new ArrayList<>();

        Account a1 = new Account("Министерство за здравство", "421231231");
        Account a2 = new Account("Министерство за финансии", "125123122");
        Account a3 = new Account("Фонд за пензиско осигурување", "41287987");
        Account a4 = new Account("Фонд за труд и социјала", "872312311");

        Budget b1 = new Budget(a1, "Основен буџет", "Здравствена дејност и осигурување", "Блок дотации", 2018, 16140905, 534300, 5345700);
        Budget b2 = new Budget(a2, "Основен буџет", "Пренесување на надлежности на ЕЛС", "Трансфери", 2018, 16140905, 534300, 5345700);
        Budget b3 = new Budget(a3, "Буџет за фондови", "Пензиско и инавлидско осигурување", "Трансфери до Фондот за ПИОМ", 2018, 16140905, 534300, 53400);
        Budget b4 = new Budget(a4, "Буџет за фондови", "Осигурување", "Блок дотации", 2018, 16140905, 534300, 5345700);

        budgetList.add(b1);
        budgetList.add(b2);
        budgetList.add(b3);
        budgetList.add(b4);

        Mockito.when(budgetService.getAllBudgetTransactions()).thenReturn(budgetList);

        mockMvc.perform(MockMvcRequestBuilders.get("/budget")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].budgetUser.name").value("Министерство за здравство"))
                .andExpect(jsonPath("$[1].budgetUser.name").value("Министерство за финансии"))
                .andExpect(jsonPath("$[2].budgetUser.name").value("Фонд за пензиско осигурување"))
                .andExpect(jsonPath("$[3].budgetUser.name").value("Фонд за труд и социјала"));
    }

    @Test
    public void getAllBudgetTransactionsIfEmpty() throws Exception {

        List<Budget> budgetList = new ArrayList<>();

        Mockito.when(budgetService.getAllBudgetTransactions()).thenReturn(budgetList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/budget").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), "No transactions were found");
    }

    @Test
    public void addBudgetTransactionReturnsOk() throws Exception {

        Account account = new Account("Ministerstvo za zdravstvo", "421231231");
        Budget budgetTransaction = new Budget(account, "Osnoven budget", "Zdravstveno osiguruvanje", "Blok dotacii", 2018, 16140905, 534300, 5345700);

        Mockito.when(budgetService.transactionExistsById(Mockito.any(Integer.class))).thenReturn(false);
        Mockito.when(budgetService.createBudgetTransaction(Mockito.any(Budget.class))).thenReturn(budgetTransaction);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/budget/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(this.objectMapper.writeValueAsBytes(budgetTransaction));

        mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(this.objectMapper.writeValueAsString(budgetTransaction)));
    }

    @Test
    public void deleteBudgetTransactionNoutFound() throws Exception {

        Mockito.when(budgetService.transactionExistsById(1050)).thenReturn(false);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/budget/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .param("id", "1050");

        MvcResult mvcResult = mockMvc.perform(builder).andExpect(status().isNotFound()).andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "Transaction with that id was not found");

    }

}









