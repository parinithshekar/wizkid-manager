package dev.owow.wizkidmanager2000.dao;

import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.exception.WizkidManagerException;
import dev.owow.wizkidmanager2000.exception.WizkidNotFoundException;
import dev.owow.wizkidmanager2000.repository.AccountRepository;
import dev.owow.wizkidmanager2000.repository.UserRepository;
import dev.owow.wizkidmanager2000.utils.UserStatus;
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
        try {
            UserEntity newUserEntity = UserEntity.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(role)
                    .email(accountEntity.getEmail())
                    .status(UserStatus.ACTIVE.toString())
                    .accountEntity(accountEntity)
                    .build();
            newUserEntity.setPhone((phone != null && !phone.isEmpty()) ? phone : null);
            newUserEntity.setPicture((picture != null && !picture.isEmpty()) ? picture : null);
            return userRepository.save(newUserEntity);
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to create new user : " + exception.getMessage(), exception);
        }
    }

    public List<UserEntity> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to fetch wizkids : " + exception.getMessage(), exception);
        }
    }

    public UserEntity findById(Long id) {
        try {
            Optional<UserEntity> user = userRepository.findById(id);
            if (user.isPresent()) {
                return user.get();
            }
            throw new WizkidNotFoundException("Wizkid not found with ID " + id);
        } catch (Exception exception) {
            if (exception instanceof WizkidNotFoundException) {
                throw exception;
            } else {
                throw new WizkidManagerException("Failed to find user : " + exception.getMessage(), exception);
            }
        }
    }

    public UserEntity updateUser(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to update user : " + exception.getMessage(), exception);
        }
    }

    public void deleteUser(Long id) {
        try {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> {
                        throw new WizkidNotFoundException("Wizkid not found with ID " + id);
                    });
            // This cascades deletes the user entry as well
            accountRepository.deleteById(userEntity.getAccountEntity().getId());
        } catch (Exception exception) {
            if (exception instanceof WizkidNotFoundException) {
                throw exception;
            } else {
                throw new WizkidManagerException("Failed to delete wizkid with ID " + id + " : " + exception.getMessage(), exception);
            }
        }
    }

    public boolean isFired(String email) {
        try {
            return userRepository.isFired(email);
        } catch (Exception exception) {
            throw new WizkidManagerException("Failed to fetch user details : " + exception.getMessage(), exception);
        }
    }
}
