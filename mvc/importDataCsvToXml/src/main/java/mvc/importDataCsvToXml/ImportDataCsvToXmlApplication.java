package mvc.importDataCsvToXml;

import mvc.importDataCsvToXml.script.ImportsCsvToXml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImportDataCsvToXmlApplication {

    public static void main(String[] args) {
        ImportsCsvToXml importsCsvToXml = new ImportsCsvToXml();
        importsCsvToXml.writeDataFromCsvToXml();
        SpringApplication.run(ImportDataCsvToXmlApplication.class, args);
    }

}
