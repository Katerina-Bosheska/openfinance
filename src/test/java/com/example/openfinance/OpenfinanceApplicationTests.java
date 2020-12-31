package com.example.openfinance;

import com.example.openfinance.model.Account;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SpringBootTest
class OpenfinanceApplicationTests {


    @Test
    void contextLoads() {
    }


}
