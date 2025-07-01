package uz.qarzon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.UserRequest;
import uz.qarzon.dto.response.UserResponse;
import uz.qarzon.entity.User;
import uz.qarzon.entity.enums.Role;
import uz.qarzon.exception.ResourceNotFoundException;
import uz.qarzon.mapper.UserMapper;
import uz.qarzon.repository.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<UserResponse> getAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getById(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        return UserMapper.toResponse(user);
    }

    public UserResponse updateUser(UserRequest userRequest, Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserMapper.toResponse(userRepo.save(user));
    }

    public void deleteUser(Integer id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    public UserResponse changeRole(Integer id, Role newRole) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        user.setRole(newRole);
        return UserMapper.toResponse(userRepo.save(user));
    }


}
