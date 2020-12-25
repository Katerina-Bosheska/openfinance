package com.example.openfinance.repository;

import com.example.openfinance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByName(String name);

    List<Account> findAllByEDB(String edb);

    List<Account> findAllByBillNumber(String billNumber);

    List<Account> findAllByNameContaining(String keyword);

    List<Account> findAllByType(int type);

    List<Account> findAllByOrderByPaidAmountDesc();

    List<Account> findAllByOrderByReceivedAmountDesc();
}
