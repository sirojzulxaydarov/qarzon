package uz.qarzon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.UserDTO;
import uz.qarzon.entity.User;
import uz.qarzon.mapper.UserMapper;
import uz.qarzon.repository.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<UserDTO> getAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        return UserMapper.toDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO, String rawPassword) {
        User user = UserMapper.toEntity(userDTO, rawPassword);
        userRepo.save(user);
        return UserMapper.toDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, Integer id, String rawPassword) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        if (rawPassword != null && !rawPassword.isEmpty()) {
            user.setPassword(rawPassword);
        }
        return UserMapper.toDTO(userRepo.save(user));
    }

    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

}
