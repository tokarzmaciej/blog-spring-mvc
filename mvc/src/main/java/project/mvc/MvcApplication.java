package project.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import project.mvc.imports.ImportsCsvToXml;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class MvcApplication {

    public static void main(String[] args) {

        ImportsCsvToXml importsCsvToXml = new ImportsCsvToXml();
        importsCsvToXml.writeDataFromCsvToXml();

        SpringApplication.run(MvcApplication.class, args);

    }
}
