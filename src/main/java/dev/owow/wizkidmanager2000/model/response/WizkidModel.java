package dev.owow.wizkidmanager2000.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WizkidModel {
    private Long wizkidId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String phone;
    private String picture;
}
