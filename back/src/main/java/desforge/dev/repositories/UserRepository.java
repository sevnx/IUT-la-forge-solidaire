package desforge.dev.repositories;

import desforge.dev.entities.Tool;
import desforge.dev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    User findByIdUser(UUID idUser);
    boolean existsByUsername(String username);
}
