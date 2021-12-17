package project.mvc.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ImageFileTypeValidator implements ConstraintValidator<IsImageFile, MultipartFile> {
    public void initialize(IsImageFile constraint) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.requireNonNull(file.getOriginalFilename()).endsWith(".png") ||
                Objects.requireNonNull(file.getOriginalFilename()).endsWith(".jpg");

    }
}
