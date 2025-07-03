package net.internetshop61efs.service;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.repository.ConfirmationCodeRepository;
import net.internetshop61efs.service.exception.NotFoundException;
import net.internetshop61efs.service.mail.MailUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;
    private final MailUtil mailUtil;

    private final int EXPIRATION_PERIOD = 1;

    private final String LINK_PATH = "localhost:8080/api/code/confirmation?code=";

    // ----------------------

    public void confirmationCodeHandle(User newuser) {
        ConfirmationCode confirmationCode = createConfirmationCode(newuser);
        repository.save(confirmationCode);
        // отправка кода по email
        sendCodeByEmail(newuser, confirmationCode);
    }

    private void sendCodeByEmail(User newuser, ConfirmationCode confirmationCode) {

        String link = LINK_PATH + confirmationCode.getCode();
        String subject = "Code confirmation email";

        System.out.println("для пользователя " + newuser.getEmail() + "отправили код по почте : " + confirmationCode.getCode());

        mailUtil.sendMail(
                newuser.getFirstName(),
                newuser.getLastName(),
                link,
                subject,
                newuser.getEmail()
                );

    }

    private ConfirmationCode createConfirmationCode(User newuser) {

        String code = generateConfirmationCode();

        return ConfirmationCode.builder()
                .code(code)
                .user(newuser)
                .expireDateTime(LocalDateTime.now().plusDays(EXPIRATION_PERIOD))
                .build();
    }

    private String generateConfirmationCode() {
        /*
        UUID - universal unique identifier
        формат 128 bit
        xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        где каждый символ 'x' - это либо цифра либо символ от a-f
        3f29c3b2-9fc2-11ed-a8fc-0242ac120002
         */
        return UUID.randomUUID().toString();

    }

    //------- поиск данных о коде при его подтверждении ------

    public ConfirmationCode findCodeInDatabase(String code){
        ConfirmationCode confirmationCode =
                repository.findByCodeAndExpireDateTimeAfter(code, LocalDateTime.now())
                        .orElseThrow(() -> new NotFoundException("Код подтерждения не найден или его срок действия истек"));

        return confirmationCode;
    }


    public List<ConfirmationCode> findCodesByUser(User user) {
        return repository.findByUser(user);
    }


    public void changeConfirmStatus(String code){
        ConfirmationCode confirmationCode = findCodeInDatabase(code);
        confirmationCode.setConfirmed(true);
        repository.save(confirmationCode);
    }

}
