package project.mvc.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;


public class CsvFileTypeValidator implements ConstraintValidator<IsCsvFile, MultipartFile> {
    public void initialize(IsCsvFile constraint) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv");

    }
}

