package net.internetshop61efs.repository;

import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    List<ConfirmationCode> findByUser(User user);
    Optional<ConfirmationCode> findByCodeAndExpiredDataTimeAfter(String code, LocalDateTime currentTime);

    Optional<ConfirmationCode> findByCode(String code);

}
