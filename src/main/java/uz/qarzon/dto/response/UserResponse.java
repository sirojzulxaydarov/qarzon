package uz.qarzon.dto.response;

import lombok.*;
import uz.qarzon.entity.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private Role role;

}
