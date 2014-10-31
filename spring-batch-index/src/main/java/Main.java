import java.io.IOException;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:beans.xml")
public class Main {


    public static void main(String[] args) throws IOException {

       //ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        SpringApplication.exit(ctx);
    }

}