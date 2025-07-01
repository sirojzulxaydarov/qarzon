package uz.qarzon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.LoginRequest;
import uz.qarzon.dto.request.RegisterRequest;
import uz.qarzon.dto.response.AuthResponse;
import uz.qarzon.entity.User;
import uz.qarzon.entity.enums.Role;
import uz.qarzon.exception.AlreadyExistsException;
import uz.qarzon.exception.BadRequestException;
import uz.qarzon.repository.UserRepo;
import uz.qarzon.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepo.existsByUsername(registerRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        if (userRepo.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new AlreadyExistsException("Phone number already exists");
        }

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .username(registerRequest.getUsername())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.OWNER)
                .build();

        userRepo.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }


    public AuthResponse login(LoginRequest loginRequest) {

        User user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }


}
