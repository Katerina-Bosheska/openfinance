package com.example.openfinance.web;

import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/number")
public class StatisticsAPI {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping()
    public List<Integer> getStatistics(){
        List<Integer> data = new ArrayList<>();
        data.add(statisticsService.getNumberOfTransactions());
        data.add(statisticsService.getNumberOfPublicInstitutions());
        data.add(statisticsService.getNumberOfComapanies());
        data.add(statisticsService.getNumberOfIndividuals());
        return data;
    }

    @GetMapping("/publicinstitutions")
    public int getNumberOfPublicInstitutions(){
        return statisticsService.getNumberOfPublicInstitutions();
    }

    @GetMapping("/companies")
    public int getNumberOfCompanies(){
        return statisticsService.getNumberOfComapanies();
    }

    @GetMapping("/individuals")
    public int getNumberOfIndividuals(){
        return statisticsService.getNumberOfIndividuals();
    }

//    @GetMapping("/transactions")
//    public List<AccountTransaction> getTransactionsOfMonth() throws ParseException {
//        Date date = new Date();
//        return statisticsService.topPayersOfMonth();
//    }
}
