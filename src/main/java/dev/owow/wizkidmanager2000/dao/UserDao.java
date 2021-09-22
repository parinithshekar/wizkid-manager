package dev.owow.wizkidmanager2000.dao;

import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.exception.WizkidNotFoundException;
import dev.owow.wizkidmanager2000.repository.AccountRepository;
import dev.owow.wizkidmanager2000.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public UserEntity createUser(String firstName, String lastName, String role, String phone, String picture, AccountEntity accountEntity) {
        UserEntity newUserEntity = UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .email(accountEntity.getEmail())
                .accountEntity(accountEntity)
                .build();
        newUserEntity.setPhone((phone != null && !phone.isEmpty()) ? phone : null);
        newUserEntity.setPicture((picture != null && !picture.isEmpty()) ? picture : null);
        return userRepository.save(newUserEntity);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new WizkidNotFoundException("Wizkid with ID " + id + " not found!");
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new WizkidNotFoundException("Wizkid not found with ID " + id);
                });
        // This cascades deletes the user entry as well
        accountRepository.deleteById(userEntity.getAccountEntity().getId());
    }
}
