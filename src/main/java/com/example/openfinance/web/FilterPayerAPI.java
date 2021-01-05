package com.example.openfinance.web;

import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.TransactionService;
import com.example.openfinance.service.exception.AccountException;
import com.example.openfinance.service.exception.TransactionNotFoundException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payer/filter")
public class FilterPayerAPI {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/date")
    List<AccountTransaction> findAllByPayerAndDate(@RequestParam int payerid,
                                                   @RequestParam String dateStr){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate date;
        long millis = formatter.parseMillis(dateStr);
        date = new LocalDate(millis);
        return transactionService.findAllByPayerAndDate(payerid, date);
    }

    @GetMapping("/konto")
    List<AccountTransaction> findAllByPayerAndKonto(@RequestParam int payerid,
                                                    @RequestParam String konto){
        return transactionService.findAllByPayerAndKonto(payerid, konto);
    }

    @GetMapping("/program")
    List<AccountTransaction> findAllByPayerAndProgram(@RequestParam int payerid,
                                                      @RequestParam String program){
        return transactionService.findAllByPayerAndProgram(payerid, program);
    }

    @GetMapping("/amount")
    List<AccountTransaction> findAllByPayerAndAmount(@RequestParam int payerid,
                                                     @RequestParam double from,
                                                     @RequestParam double to){
        return transactionService.findAllByPayerAndAmount(payerid, from, to);
    }

    @GetMapping("/recipient")
    List<AccountTransaction> findAllByPayerAndRecipient(@RequestParam int payerid,
                                                        @RequestParam String name) throws AccountException, TransactionNotFoundException {
        return transactionService.findAllByPayerAndRecipient(payerid, name);
    }
}
