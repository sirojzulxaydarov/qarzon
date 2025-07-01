package uz.qarzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qarzon.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
}
