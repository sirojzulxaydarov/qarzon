package uz.qarzon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.qarzon.entity.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private Role role;

}
