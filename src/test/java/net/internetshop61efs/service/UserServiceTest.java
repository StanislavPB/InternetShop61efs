package net.internetshop61efs.service;


import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.repository.ConfirmationCodeRepository;
import net.internetshop61efs.repository.UserRepository;
import net.internetshop61efs.service.exception.AlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp(){
        User testUser = new User();
        testUser.setFirstName("user1");
        testUser.setLastName("user1");
        testUser.setEmail("user1@gmail.com");
        testUser.setHashPassword("Pass12345!");
        testUser.setRole(User.Role.USER);
        testUser.setState(User.State.NOT_CONFIRMED);
        User savedUser = userRepository.save(testUser);

        ConfirmationCode code = new ConfirmationCode();
        code.setCode("someConfirmationCode");
        code.setUser(savedUser);
        code.setExpiredDataTime(LocalDateTime.now().plusDays(1));
        confirmationCodeRepository.save(code);
    }

    @AfterEach
    void drop(){
        confirmationCodeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testWhenDuplicatedEmail(){
        UserRequestDto requestDto = new UserRequestDto(
                "firstUserName",
                "lastUserName",
                "user1@gmail.com",
                "Pass12345!"
        );

        assertThrows(AlreadyExistException.class, () -> userService.registration(requestDto));

    }



}
