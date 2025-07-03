package net.internetshop61efs.service;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.repository.UserRepository;
import net.internetshop61efs.service.exception.AlreadyExistException;
import net.internetshop61efs.service.exception.NotFoundException;
import net.internetshop61efs.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Converter converter;
    private final ConfirmationCodeService confirmationCodeService;

    public UserResponseDto registration(UserRequestDto request){
        /*
        1) проверить а нет ли уже такого пользователя
        2) создать нового пользователя используя данные из request
        3) дополнить нового пользователя данными из системы )роль, время создания и тд)
        4) сохранить
        5) отправить код подтверждения
         */

        if (repository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("User with email: " + request.getEmail() + " is already exist");
        }

        // ----- а вот если такого пользователя еще нет -----

        User newUser = converter.fromDto(request);

        newUser.setRole(User.Role.USER);
        newUser.setStatus(User.Status.NOT_CONFIRMED);

        User savedUser = repository.save(newUser);

        // после создания новго пользователя необходимо создать
        // новый код подтверждения для него и отправить ему на почту

        confirmationCodeService.confirmationCodeHandle(savedUser);

        return converter.toDto(savedUser);
    }

    public List<UserResponseDto> findAllUsers(){
        List<User> users = repository.findAll();
        List<UserResponseDto> responses = converter.fromUsers(users);
        return responses;
    }

    public UserResponseDto findUserById(Integer id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id = " + id + " not found"));
        return converter.toDto(user);
    }

    public UserResponseDto findUserByEmail(String email){
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email : " + email + " not found"));
        return converter.toDto(user);
    }

    public List<User> findFullDetailsUsers(){
        return repository.findAll();
    }

    public User findFullDetailsUserById( Integer id){
        return repository.findById(id).get(); // !!!!!!!! временно делаем без проверки на null !!!!!!
    }


    // -------- действия при получении запрос о подтверждении почты -------

    public UserResponseDto confirmationEmail(String code){
        ConfirmationCode confirmationCode = confirmationCodeService.findCodeInDatabase(code);
        User user = confirmationCode.getUser();
        confirmationCodeService.changeConfirmStatus(code);
        user.setStatus(User.Status.CONFIRMED);
        repository.save(user);

        return converter.toDto(user);
    }

    public UserResponseDto updateUser(UserRequestDto updateRequest){
        String userEmail = updateRequest.getEmail();

        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("Email must be provide to update user");
        }

        // найдем пользователя по email

        User userByEmail = repository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User with email:" + userEmail + " not found"));

        /*
        обновляем ВСЕ доступные поля
        Так как мы заранее НЕ ЗНАЕМ, а какие именно поля пользоваетель захочет поменять,
        то есть в JSON (в теле запроса), будут находиться только ТЕ поля (со знаячениями),
        которые пользователь хочет поменять (не обязательно все)
         */

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isBlank()) {
            userByEmail.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isBlank()) {
            userByEmail.setLastName(updateRequest.getLastName());
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
            userByEmail.setHashPassword(updateRequest.getPassword());
        }

        // созраняем (обновляем) пользователя

        repository.save(userByEmail);

        return converter.toDto(userByEmail);
    }

    public boolean renewCode(String email){
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + "not found"));

        confirmationCodeService.confirmationCodeHandle(user);
        return true;
    }
}
