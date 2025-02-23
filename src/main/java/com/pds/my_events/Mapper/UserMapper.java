package com.pds.my_events.Mapper;

import com.pds.my_events.dto.UserDTO;
import com.pds.my_events.Model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getCPF(),
                user.getEnrollment(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setCPF(userDTO.getCpf());
        user.setEnrollment(userDTO.getEnrollment());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}

