package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.mvc.validators.IsCsvFile;
import project.mvc.validators.IsNotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportComments {
    @IsNotEmpty
    @IsCsvFile
    private MultipartFile fileCsv;
}
