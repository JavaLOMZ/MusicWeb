import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


//@SpringBootApplication(scanBasePackages = {"junior.academy"}, exclude = JpaRepositoriesAutoConfiguration.class)
//public class Application {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//}



@SpringBootApplication
@ComponentScan(basePackages = {"junior.academy"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}