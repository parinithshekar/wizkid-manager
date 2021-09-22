package dev.owow.wizkidmanager2000.model.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewWizkidModel {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String phone;
    private String picture;
}
