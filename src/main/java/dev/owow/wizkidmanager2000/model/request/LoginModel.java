package dev.owow.wizkidmanager2000.model.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
    private String email;
    private String password;
}
