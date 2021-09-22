package dev.owow.wizkidmanager2000.model.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateWizkidModel {
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String phone;
    private String picture;
}
