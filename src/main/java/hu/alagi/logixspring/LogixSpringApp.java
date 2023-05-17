package hu.alagi.logixspring;

import hu.alagi.logixspring.service.InitDBService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogixSpringApp implements CommandLineRunner {

    private final InitDBService initDbService;

    public LogixSpringApp(InitDBService initDbService) {
        this.initDbService = initDbService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LogixSpringApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.clearDB();
        initDbService.insertTestData();
    }
}

