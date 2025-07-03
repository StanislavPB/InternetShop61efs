package net.internetshop61efs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import net.internetshop61efs.entity.FileInfo;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.repository.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileInfoRepository repository;
    private final AmazonS3 amazonS3;
    private final UserService userService;

    public String upload(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        // получаем исходное имя файла

        String extension = "";

        if (originalFileName != null){
            int indexExtension = originalFileName.lastIndexOf(".") + 1;
            // получаем индекс начала расширения нашего файла

            extension = originalFileName.substring(indexExtension);
        } else {
            throw new NullPointerException("Null original file name");
        }

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + "." + extension;

        // загрузка файла в Digital Ocean

        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        // создаем запрос на отправку файла

        PutObjectRequest request = new PutObjectRequest(
                "demo-shop-files",
                "data/" + newFileName,
                inputStream,
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);

        // выполение запроса - то есть файл ушел на сервер
        amazonS3.putObject(request);

        // сформировать ссылку на этот внешнее хранилище для нашего файла

        String link = amazonS3.getUrl("demo-shop-files","data/" + newFileName).toString();

        // сохраним эту ссылку у нас в БД
        // мы должны получить данные о том user для кого этот файл предназначен
        // эта опция (загрузка файла) будет доступна только зарегистрированным пользователям
        // так как мы будем подключать Spring Security, то информация о том какой пользователь
        // отправил нам запрос будет хранится в Security Context и мы от туда ее возьмем

        // пока для текущего решения используем User с id = 1

        User user = userService.findFullDetailsUserById(1);


        FileInfo fileInfo = new FileInfo();
        fileInfo.setLink(link);
        fileInfo.setUser(user);

        repository.save(fileInfo);

        return "Файл " + link + " успешно сохранен";

    }

}
