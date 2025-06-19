package uz.qarzon.mapper;

import uz.qarzon.dto.UserDTO;
import uz.qarzon.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .username(user.getUsername())
                .build();
    }

    public static User toEntity(UserDTO dto, String password) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .phoneNumber(dto.getPhoneNumber())
                .password(password)
                .role(dto.getRole())
                .build();

    }


}
