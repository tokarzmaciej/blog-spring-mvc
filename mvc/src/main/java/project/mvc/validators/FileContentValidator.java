package project.mvc.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileContentValidator implements ConstraintValidator<IsNotEmpty, MultipartFile> {
    public void initialize(IsNotEmpty constraint) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return !file.isEmpty();
    }
}
