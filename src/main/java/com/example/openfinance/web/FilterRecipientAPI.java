package com.example.openfinance.web;

import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.service.FilterRecipientService;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/recipient/filter")
public class FilterRecipientAPI {

    @Autowired
    private FilterRecipientService recipientService;

    @GetMapping("/date")
    public List<AccountTransaction> findAllByRecipientAndDate(@RequestParam int recipientid,
                                                              @RequestParam String date){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = formatter.parseMillis(date);
        LocalDate dateObj = new LocalDate(millis);
        return recipientService.findAllByRecipientAndDate(recipientid, dateObj);
    }

    @GetMapping("/konto")
    List<AccountTransaction> findAllByRecipientAndKonto(@RequestParam int recipientid,
                                                        @RequestParam String konto){
        return recipientService.findAllByRecipientAndKonto(recipientid, konto);
    }

    @GetMapping("/program")
    List<AccountTransaction> findAllByRecipientAndProgram(@RequestParam int recipientid,
                                                          @RequestParam String program){
        return recipientService.findAllByRecipientAndProgram(recipientid, program);
    }

    @GetMapping("/bill")
    List<AccountTransaction> findAllByRecipientAndBill(@RequestParam int recipientid,
                                                       @RequestParam String bill){
        return recipientService.findAllByRecipientAndBill(recipientid, bill);
    }

    @GetMapping("/payer")
    List<AccountTransaction> findAllByRecipientAndPayerName(@RequestParam int recipientid,
                                                            @RequestParam String payerName){
        return recipientService.findAllByRecipientAndPayerName(recipientid, payerName);
    }

    @GetMapping("/amount")
    List<AccountTransaction> findAllByRecipientAndAmountGreaterThanAndAmountLessThan(@RequestParam int recipientid,
                                                                                     @RequestParam double from,
                                                                                     @RequestParam double to){
        return recipientService.findAllByRecipientAndAmountGreaterThanAndAmountLessThan(recipientid, from, to);
    }
}
