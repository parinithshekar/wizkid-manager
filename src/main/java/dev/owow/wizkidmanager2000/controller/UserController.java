package dev.owow.wizkidmanager2000.controller;

import dev.owow.wizkidmanager2000.dao.AccountDao;
import dev.owow.wizkidmanager2000.dao.UserDao;
import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.exception.FiredException;
import dev.owow.wizkidmanager2000.exception.WizkidManagerException;
import dev.owow.wizkidmanager2000.exception.WizkidNotFoundException;
import dev.owow.wizkidmanager2000.model.request.UpdateWizkidModel;
import dev.owow.wizkidmanager2000.utils.UserStatus;
import dev.owow.wizkidmanager2000.utils.WizkidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wizkid")
@Slf4j
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    /**
     * Fire a wizkid, this wizkid will no longer be able to login and access other features
     *
     * @param id
     * @return Success or Error message
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/fire/{id}")
    ResponseEntity<?> fire(@PathVariable Long id) {
        try {
            UserEntity userEntity = userDao.findById(id);
            if (userEntity.getStatus().equals(UserStatus.FIRED.toString())) {
                return ResponseEntity.ok().body("This wizkid has already been fired");
            }
            userEntity.setStatus(UserStatus.FIRED.toString());
            userDao.updateUser(userEntity);
            return ResponseEntity.ok().body("Wizkid with ID " + id + " is fired!");
        } catch (Exception exception) {
            if (exception instanceof WizkidNotFoundException || exception instanceof WizkidManagerException) {
                throw exception;
            } else {
                throw new WizkidManagerException("Failed to fire wizkid : " + exception.getMessage(), exception);
            }
        }
    }

    /**
     * Unfire a wizkid, this wizkid will be reinstated at OWOW and be able to access all features again
     *
     * @param id
     * @return Success or Error message
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/unfire/{id}")
    ResponseEntity<?> unfire(@PathVariable Long id) {
        try {
            UserEntity userEntity = userDao.findById(id);
            if (userEntity.getStatus().equals(UserStatus.ACTIVE.toString())) {
                return ResponseEntity.ok().body("This wizkid has already been re-instated");
            }
            userEntity.setStatus(UserStatus.ACTIVE.toString());
            userDao.updateUser(userEntity);
            return ResponseEntity.ok().body("Wizkid with ID " + id + " is now back at OWOW!");
        } catch (Exception exception) {
            if (exception instanceof WizkidNotFoundException || exception instanceof WizkidManagerException) {
                throw exception;
            } else {
                throw new WizkidManagerException("Failed to unfire wizkid : " + exception.getMessage(), exception);
            }
        }
    }

    /**
     * Update the details of a wizkid. This method allows to update all data including the phone and email
     *
     * @param updateWizkidModel
     * @param id
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/details")
    ResponseEntity<?> updateDetails(@RequestBody UpdateWizkidModel updateWizkidModel, @PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);

        if (userEntity.getStatus().equals(UserStatus.FIRED.toString())) {
            log.error("Cannot update details as this user is fired!");
            throw new FiredException("Cannot update user details as user is fired!");
        }

        AccountEntity accountEntity = userEntity.getAccountEntity();
        accountEntity.setEmail(updateWizkidModel.getEmail());

        userEntity.setFirstName(updateWizkidModel.getFirstName());
        userEntity.setLastName(updateWizkidModel.getLastName());
        userEntity.setEmail(updateWizkidModel.getEmail());
        userEntity.setRole(updateWizkidModel.getRole());
        userEntity.setPhone(updateWizkidModel.getPhone());
        userEntity.setPicture(updateWizkidModel.getPicture());
        userEntity.setAccountEntity(accountDao.updateAccount(accountEntity));

        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(userDao.updateUser(userEntity)));
    }
}
