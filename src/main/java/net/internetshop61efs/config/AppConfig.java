package net.internetshop61efs.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class AppConfig {

    @Bean
    public Configuration freemakerConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(AppConfig.class, "/mail/"));
        return configuration;
    }


    @Bean
    public AmazonS3 amazonS3(S3ConfigurationProperties properties) {
        // аутентификация нашего приложения в digitalOcean

        AWSCredentials credentials = new BasicAWSCredentials(
                properties.getAccessKey(),
                properties.getSecretKey()
        );

        // настройка точки подключения к хранилищу
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                properties.getEndpoint(), properties.getRegion()
        );

        // создать клиента для загрузки файлов

        AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));
        amazonS3ClientBuilder.setEndpointConfiguration(endpointConfiguration);

        AmazonS3 amazonS3 = amazonS3ClientBuilder.build(); // клиент для амазона / digital ocean -
        // экземпляр класса который содержит всю информацию о месте подключения и права доступа

        return amazonS3;
    }

}
