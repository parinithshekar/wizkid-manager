package dev.owow.wizkidmanager2000.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWizkidGuestModel {
    private String firstName;
    private String lastName;
}
