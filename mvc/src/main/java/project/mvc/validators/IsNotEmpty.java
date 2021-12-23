package project.mvc.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileContentValidator.class)
public @interface IsNotEmpty {

    String message() default "The file is empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
