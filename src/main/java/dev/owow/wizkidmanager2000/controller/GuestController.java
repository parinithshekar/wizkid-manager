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

    /**
     * Fetch all the wizkids that exist
     * @return
     */
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

    /**
     * Fetch a single wizkid by wizkid ID
     * @param id
     * @return WizkidModel
     */
    @GetMapping("/{id}")
    ResponseEntity<?> getWizkid(@PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);
        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(userEntity));
    }

    /**
     * Update the first or last name of an existing wizkid
     * @param updateWizkidGuestModel
     * @param id
     * @return WizkidModel
     */
    @PutMapping("/{id}")
    ResponseEntity<?> updateWizkid(@RequestBody UpdateWizkidGuestModel updateWizkidGuestModel, @PathVariable Long id) {
        UserEntity userEntity = userDao.findById(id);
        if (userEntity.getStatus().equals(UserStatus.FIRED.toString())) {
            log.error("Cannot update user details as user is fired");
            throw new FiredException("Cannot update user details as user is fired");
        }
        userEntity.setFirstName(updateWizkidGuestModel.getFirstName());
        userEntity.setLastName(updateWizkidGuestModel.getLastName());
        log.info("Details updated for wizkid ID {}", id);
        return ResponseEntity.ok()
                .body(WizkidUtils.toModel(userDao.updateUser(userEntity)));
    }

    /**
     * Delete a wizkid from the database
     * @param id
     */
    @DeleteMapping("/{id}")
    void deleteWizkid(@PathVariable Long id) {
        log.info("Wizkid with ID {} deleted", id);
        userDao.deleteUser(id);
    }

    /**
     * Search for a wizkid based on the name and role
     * @param search
     * @param role
     * @return All wiskids that match search criteria
     */
    @GetMapping("/search")
    ResponseEntity<?> searchWizkids(@RequestParam String search, @RequestParam String role) {
        List<WizkidModel> wizkids = userDao.search(search, role).stream().map(WizkidUtils::toModel).collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(wizkids);
    }

}
