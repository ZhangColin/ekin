package com.ekin.system.resource.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author colin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResourceCategoryExistsValidator.class)
public @interface ResourceCategoryExists {
    String message() default "资源分类不存在";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
