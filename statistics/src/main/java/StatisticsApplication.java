import component.IndexAndMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"component"})
@SpringBootApplication
public class StatisticsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

    @Autowired
    private IndexAndMapping indexAndMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        indexAndMapping.checkAndUpdate();
    }
}