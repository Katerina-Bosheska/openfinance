package com.example.openfinance;

import com.example.openfinance.model.Account;
import com.example.openfinance.service.AccountService;
import com.example.openfinance.service.exception.AccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTests {

    @Autowired
    private AccountService accountService;

    // TEST EDIT ACCOUNT

    //newAccount isn't null, the id exists in db
    @Test
    void testEditAccountA1B1() throws AccountException {
        Account newAccount = new Account("testAkaunt", "testEdb", "testBill", "testAddr", 1, 0, 0);
        accountService.editAccount(1, newAccount);
        Account editedAccount = accountService.getAccountById(1);
        Assertions.assertEquals(newAccount.getEDB(), editedAccount.getEDB());
        Assertions.assertEquals(newAccount.getName(), editedAccount.getName());
        Assertions.assertEquals(newAccount.getBillNumber(), editedAccount.getBillNumber());
        Assertions.assertEquals(newAccount.getAddress(), editedAccount.getAddress());
    }

    //newAccount isn't null, the id doesn't exist in db
    // here we expect the function to throw an exception
    @Test
    void testEditAccountA1B2() throws AccountException {
        Account newAccount = new Account("testAkaunt", "", "", "", 1, 0, 0);
        Assertions.assertThrows(AccountException.class, () -> accountService.editAccount(-2, newAccount));
    }

    // newAccount is null, the id exists in db
    // should throw an exception
    @Test
    void testEditAccountA2B1() throws AccountException {
        Assertions.assertThrows(AccountException.class, () -> accountService.editAccount(1, null));
    }

    // newAccount is null, the id doesn't exist in db
    // should throw an exception
    @Test
    void testEditAccountA2B2() throws AccountException {
        Assertions.assertThrows(AccountException.class, () -> accountService.editAccount(-2, null));
    }

}
