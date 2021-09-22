package dev.owow.wizkidmanager2000.controller;

import dev.owow.wizkidmanager2000.dao.AccountDao;
import dev.owow.wizkidmanager2000.dao.UserDao;
import dev.owow.wizkidmanager2000.entity.AccountEntity;
import dev.owow.wizkidmanager2000.exception.FiredException;
import dev.owow.wizkidmanager2000.exception.WizkidManagerException;
import dev.owow.wizkidmanager2000.model.request.LoginModel;
import dev.owow.wizkidmanager2000.security.UserDetails;
import dev.owow.wizkidmanager2000.security.UserDetailsService;
import dev.owow.wizkidmanager2000.security.jwt.JWTOperations;
import dev.owow.wizkidmanager2000.utils.CommonUtils;
import dev.owow.wizkidmanager2000.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTOperations jwtOperations;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
        String email = loginModel.getEmail();
        String password = loginModel.getPassword();
        try {
            if (userDao.isFired(email)) {
                throw new FiredException("You have been recently fired!");
            }
            ValidationUtils.areNonEmptyNorNull(List.of(email, password));
            UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(credentials);
            log.info("User logged in with email : {}", email);
        } catch (Exception exception) {
            if (exception instanceof FiredException) {
                throw exception;
            }
            log.error("Invalid credentials for log in", exception);
            throw new WizkidManagerException("Invalid credentials for login");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        HttpHeaders httpHeaders = new HttpHeaders(CommonUtils.getCorsHeaders());
        httpHeaders.set(HttpHeaders.SET_COOKIE, "WizkidAuthCookie=" + jwtOperations.createToken(userDetails));

        return ResponseEntity.ok().headers(httpHeaders).body("LOGIN SUCCESS");
    }
}
