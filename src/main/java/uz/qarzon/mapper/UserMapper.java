package uz.qarzon.mapper;

import uz.qarzon.dto.request.UserRequest;
import uz.qarzon.dto.response.UserResponse;
import uz.qarzon.entity.User;
import uz.qarzon.entity.enums.Role;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserRequest userRequest, String encodedPassword) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(encodedPassword)
                .build();
    }

}
