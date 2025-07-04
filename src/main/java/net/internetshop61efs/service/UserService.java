package net.internetshop61efs.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.internetshop61efs.dto.MessageResponseDto;
import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.repository.UserRepository;
import net.internetshop61efs.service.exception.AlreadyExistException;
import net.internetshop61efs.service.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationCodeService confirmationCodeService;
    private final Converter converter;


    @Transactional
    public UserResponseDto registration(UserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Пользователь с email: " + request.getEmail() + " уже зарегистрирован");
        }

//        User user = User.builder()
//                .email(request.getEmail())
//                .firstName(request.getFirstName())
//                .secondName(request.getSecondName())
//                .hashPassword(request.getHashPassword())
//                .role(User.Role.USER)
//                .state(User.State.NOT_CONFIRMED)
//                .build();

        User user = converter.fromDto(request);
        user.setRole(User.Role.USER);
        user.setState(User.State.NOT_CONFIRMED);

        userRepository.save(user);

        confirmationCodeService.saveConfirmationCode(user);

        return converter.fromUser(user);
    }

    public List<UserResponseDto> findAll() {
        return converter.fromUser(userRepository.findAll());
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID = " + userId + " не найден"));
        return converter.fromUser(user);
    }

    public List<User> findAllFullDetails() {
        return userRepository.findAll();
    }

    @Transactional
    public UserResponseDto confirmation(String confirmationCode) {

        User user = confirmationCodeService.findCodeInDatabase(confirmationCode);

        user.setState(User.State.CONFIRMED);

        userRepository.save(user);

        confirmationCodeService.confirmStatus(confirmationCode);

        return converter.fromUser(user);
    }

    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }

    public MessageResponseDto setPhotoLink(String fileLink) {
        User currentUser = getCurrentUser();
        currentUser.setPhotoLink(fileLink);
        userRepository.save(currentUser);
        return new MessageResponseDto("Ссылка на файл успешно обновлена");
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return findByEmail(email);
    }

    public List<ConfirmationCode> findCodesByEmail(String email){
        User user = findByEmail(email);
        return confirmationCodeService.findCodesByUser(user);
    }

}
