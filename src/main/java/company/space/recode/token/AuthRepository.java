package company.space.recode.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUserId(String userId);
    Optional<Token> findByUserIdAndToken(String userId, String token);
    Integer deleteByUserId(String userId);
}
