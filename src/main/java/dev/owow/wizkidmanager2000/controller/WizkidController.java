package dev.owow.wizkidmanager2000.controller;

import dev.owow.wizkidmanager2000.dao.AccountDao;
import dev.owow.wizkidmanager2000.dao.UserDao;
import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.model.request.NewWizkidModel;
import dev.owow.wizkidmanager2000.model.request.UpdateWizkidModel;
import dev.owow.wizkidmanager2000.model.response.WizkidModel;
import dev.owow.wizkidmanager2000.utils.WizkidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wizkid")
public class WizkidController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @GetMapping()
    List<WizkidModel> all() {
        List<WizkidModel> allWizkids = userDao.findAll().stream().map(WizkidUtils::toModel).collect(Collectors.toList());
        return allWizkids;
    }

    @PostMapping()
    WizkidModel createWizkid(@RequestBody NewWizkidModel newWizkidModel) {
        AccountEntity newAccountEntity = accountDao.createAccount(newWizkidModel.getEmail(), newWizkidModel.getPassword());
        UserEntity newUserEntity = userDao.createUser(
                newWizkidModel.getFirstName(),
                newWizkidModel.getLastName(),
                newWizkidModel.getRole(),
                newWizkidModel.getPhone(),
                newWizkidModel.getPicture(),
                newAccountEntity
        );
        return WizkidUtils.toModel(newUserEntity);
    }

    @PutMapping("/{id}")
    WizkidModel updateWizkid(@RequestBody UpdateWizkidModel updateWizkidModel, @PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);
        AccountEntity accountEntity = userEntity.getAccountEntity();
        accountEntity.setEmail(updateWizkidModel.getEmail());

        userEntity.setFirstName(updateWizkidModel.getFirstName());
        userEntity.setLastName(updateWizkidModel.getLastName());
        userEntity.setEmail(updateWizkidModel.getEmail());
        userEntity.setRole(updateWizkidModel.getRole());
        userEntity.setPhone(updateWizkidModel.getPhone());
        userEntity.setPicture(updateWizkidModel.getPicture());
        userEntity.setAccountEntity(accountDao.updateAccount(accountEntity));

        return WizkidUtils.toModel(userDao.updateUser(userEntity));
    }

    @DeleteMapping("/{id}")
    void deleteWizkid(@PathVariable Long id) {
        userDao.deleteUser(id);
    }

}
