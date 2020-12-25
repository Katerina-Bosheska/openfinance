package com.example.openfinance.service.impl;

import com.example.openfinance.model.Account;
import com.example.openfinance.model.AccountTransaction;
import com.example.openfinance.repository.AccountRepository;
import com.example.openfinance.repository.BudgetRepository;
import com.example.openfinance.repository.TransactionRepository;
import com.example.openfinance.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializeServiceImpl implements InitializeService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void loadAccounts() {
        Account account;
        // 1=javna ustanova, 2=kompanija, 3=poedinec
        account=new Account("ФОНД ЗА ЗДРАВСТВО", "4030991261703", "660026001166012", "БУЛ. МАКЕДОНИЈА ББ", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ФОНД НА ПЕНЗИСКО И ИНВАЛИДСКО ОСИГУРУВАЊЕ", "4030992242699", "660046003866012", "ВЛАДИМИР КОМАРОВ БР.11 СКОПЈЕ", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ТРУД И СОЦИЈАЛНА ПОЛИТИКА", "4030990252999", "150010029963718", "ДАМЕ ГРУЕВ 14", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("БУЏЕТ НА Р.М.", "4030990255017", "630010001963019", "", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ОБРАЗОВАНИЕ И НАУКА", "4030990253456", "160010032963719", "МИТО ХАЏИВАСИЛЕВ ЈАСМИН БР.50", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("АГЕНЦИЈА ЗА ФИНАНСИСКА ПОДДРШКА ВО ЗЕМЈОДЕЛСТВОТО И РУРАЛЕН РАЗВОЈ", "4030007639954", "140046035663718", "ТРЕТА МАКЕДОНСКА БРИГАДА БР.20", 2, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ФИНАНСИИ", "4030990255017", "090021159263710", "ДАМЕ ГРУЕВ БР.12", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ОДБРАНА", "4030990271748", "050010011663717", "ОРЦЕ НИКОЛОВ БР.116", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ВНАТРЕШНИ РАБОТИ", "4030990270610", "060010012463714", "ДИМЧЕ МИРЧЕВ БР.9", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ВЛАДА НА РЕПУБЛИКА МАКЕДОНИЈА", "4030990253430", "040010007863713", "БУЛ. ИЛИНДЕН БР.2", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ТРАНСПОРТ И ВРСКИ", "4030990278246", "130010025663718", "ДАМЕ ГРУЕВ БР.6", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ИНФОРМАТИЧКО ОПШТЕСТВО И АДМИНИСТРАЦИЈА", "4030008038199", "170016117463712", "БУЛ. СВ КИРИЛ И МЕТОДИЈ БР.54", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("АГЕНЦИЈА ЗА ВРАБОТУВАЊЕ НА РМ", "4030992138158", "660036002066019", "ВАСИЛ ЃОРЃОВ БР.43", 2, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("УПРАВА ЗА ЈАВНИ ПРИХОДИ", "4030000404218", "090051567963715", "БУЛ. КУЗМАН ЈОСИФОСКИ-ПИТУ БР.1", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ФИНАНСИРАЊЕ НА ДЕЈНОСТИТЕ ОД ОБЛАСТА НА КУЛТУРАТА", "4030990253790", "180100042663712", "", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("НАЦИОНАЛНА АГЕНЦИЈА ЗА ЕВРОПСКИ ОБРАЗОВНИ ПРОГРАМИ И МОБИЛНОСТ", "4030007012234", "160036118278501", "БУЛ. КУЗМАН ЈОСИФОСКИ-ПИТУ БР.17", 2, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ЗЕМЈОДЕЛСТВО, ШУМАРСТВО И ВОДОСТОПАНСТВО", "4030990253758", "140010028063710", "АМИНТА ТРЕТИ", 1, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("СЛУЖБА ЗА ОПШТИ И ЗАЕДНИЧКИ РАБОТИ", "4030991222325", "040020008663713", "", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ДИРЕКЦИЈА ЗА ТЕХНОЛОШКИ ИНДУСТРИСКИ РАЗВОЈНИ ЗОНИ СКОПЈЕ", "4030999393528", "100044124663717", "Булевар Партизански Одреди бр.2", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ФОНД ЗА ИНОВАЦИИ И ТЕХНОЛОШКИ РАЗВОЈ", "4080013541068", "040016201460312", "ГТЦ, локал 6, Д.Е. 220", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("ФОНД ЗА ПИОМ-СРЕДСТВА ЗА СОЛИДАРЕН ФОНД", "4030992242699", "660046003866031", "ВЛАДИМИР КОМАРОВ БР.11 СКОПЈЕ", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("МИНИСТЕРСТВО ЗА ТРУД И СОЦИЈАЛНА ПОЛИТИКА - НАМЕНСКА СМЕТКА", "4030990252999", "150010029978913", "ДАМЕ ГРУЕВ 14", 3, 0.0, 0.0);
        accountRepository.save(account);
        account=new Account("УКИМ МЕДИЦИНСКИ ФАКУЛТЕТ СКОПЈЕ", "4080009100154", "160011151778813", "50 ДИВИЗИЈА, СКОПЈЕ", 1, 0.0, 0.0);
        accountRepository.save(account);
    }

    @Override
    public void loadAccountTransactions() {

    }

    @Override
    public void loadBudgetTransactions() {

    }
}
