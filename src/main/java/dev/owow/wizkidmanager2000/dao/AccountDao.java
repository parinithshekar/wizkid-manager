package dev.owow.wizkidmanager2000.dao;

import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDao {

    @Autowired
    private AccountRepository accountRepository;

    public AccountEntity createAccount(String email, String password) {
        AccountEntity newAccountEntity = AccountEntity.builder().email(email).password(password).build();
        return accountRepository.save(newAccountEntity);
    }

    public AccountEntity updateAccount(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }
}
