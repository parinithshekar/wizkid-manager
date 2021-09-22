package dev.owow.wizkidmanager2000.controller;

import dev.owow.wizkidmanager2000.dao.AccountDao;
import dev.owow.wizkidmanager2000.dao.UserDao;
import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.exception.FiredException;
import dev.owow.wizkidmanager2000.model.request.NewWizkidModel;
import dev.owow.wizkidmanager2000.model.request.UpdateWizkidGuestModel;
import dev.owow.wizkidmanager2000.model.response.WizkidModel;
import dev.owow.wizkidmanager2000.utils.UserStatus;
import dev.owow.wizkidmanager2000.utils.WizkidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wizkid")
@Slf4j
public class GuestController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @GetMapping()
    ResponseEntity<?> all() {
        List<WizkidModel> allWizkids = userDao.findAll().stream().map(WizkidUtils::toModel).collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(allWizkids);
    }

    /**
     * The guest does the "registration" for the new user by providing the password and email
     *
     * @param newWizkidModel
     * @return WizkidModel
     */
    @PostMapping()
    ResponseEntity<?> createWizkid(@RequestBody NewWizkidModel newWizkidModel) {
        AccountEntity newAccountEntity = accountDao.createAccount(newWizkidModel.getEmail(), newWizkidModel.getPassword());
        UserEntity newUserEntity = userDao.createUser(
                newWizkidModel.getFirstName(),
                newWizkidModel.getLastName(),
                newWizkidModel.getRole(),
                newWizkidModel.getPhone(),
                newWizkidModel.getPicture(),
                newAccountEntity
        );
        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(newUserEntity));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getWizkid(@PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);
        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(userEntity));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateWizkid(@RequestBody UpdateWizkidGuestModel updateWizkidGuestModel, @PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);
        if (userEntity.getStatus().equals(UserStatus.FIRED.toString())) {
            log.error("Cannot update user details as user is fired");
            throw new FiredException("Cannot update user details as user is fired");
        }
        userEntity.setFirstName(updateWizkidGuestModel.getFirstName());
        userEntity.setLastName(updateWizkidGuestModel.getLastName());
        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(userDao.updateUser(userEntity)));
    }

    @DeleteMapping("/{id}")
    void deleteWizkid(@PathVariable Long id) {
        userDao.deleteUser(id);
    }

}
