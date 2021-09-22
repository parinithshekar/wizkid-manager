package dev.owow.wizkidmanager2000.utils;

import dev.owow.wizkidmanager2000.entity.UserEntity;
import dev.owow.wizkidmanager2000.model.response.WizkidModel;

public class WizkidUtils {

    public static WizkidModel toModel(UserEntity userEntity) {
        return WizkidModel.builder()
                .wizkidId(userEntity.getWizkidId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .role(userEntity.getRole())
                .phone(userEntity.getPhone())
                .picture(userEntity.getPicture())
                .status(userEntity.getStatus())
                .build();
    }
}
