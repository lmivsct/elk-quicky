import java.io.IOException;

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

        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        SpringApplication.exit(ctx);
    }

}