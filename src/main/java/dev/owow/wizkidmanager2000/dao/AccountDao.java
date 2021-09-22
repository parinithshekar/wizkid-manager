package dev.owow.wizkidmanager2000.dao;

import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.exception.WizkidManagerException;
import dev.owow.wizkidmanager2000.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountDao {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountEntity createAccount(String email, String password) {
        try {
        AccountEntity newAccountEntity = AccountEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return accountRepository.save(newAccountEntity);
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to create new account : " + exception.getMessage(), exception);
        }
    }

    public AccountEntity updateAccount(AccountEntity accountEntity) {
        try {
            return accountRepository.save(accountEntity);
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to update account : "+ exception.getMessage(), exception);
        }
    }
}
