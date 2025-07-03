package net.internetshop61efs.repository;

import net.internetshop61efs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // метод для проверки в процеесе регистрации нового пользователя
    boolean existsByEmail(String email);

    // метод для ответа на запрос: "предоставьте данные о пользователе с таким-то email"
    Optional<User> findByEmail(String email);


    List<User> findByLastName(String lastName);

}
