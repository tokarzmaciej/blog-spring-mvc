package project.mvc.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileTypeValidator.class)
public @interface IsImageFile {

    String message() default "The file must be in .png or .jpg format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
