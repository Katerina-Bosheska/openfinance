package com.example.openfinance.web;

import com.example.openfinance.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/load")
public class DataAPI {

    @Autowired
    private InitializeService service;

    @GetMapping("/accounts")
    public void loadAccounts(){
        service.loadAccounts();
    }

}
