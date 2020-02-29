package com.ekin.system.resource.validator;

import com.ekin.system.resource.application.ResourceCategoryAppService;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
@Constraint(validatedBy = ResourceCategoryVerify.Validator.class)
public @interface ResourceCategoryVerify {
    String message() default "资源分类不存在";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ResourceCategoryVerify, Long> {
        private final ResourceCategoryAppService resourceCategoryAppService;

        public Validator(ResourceCategoryAppService resourceCategoryAppService) {
            this.resourceCategoryAppService = resourceCategoryAppService;
        }

        @Override
        public boolean isValid(Long value, ConstraintValidatorContext context) {
            return resourceCategoryAppService.getAllResourceCategories().stream()
                    .anyMatch(resourceCategoryDto -> resourceCategoryDto.getId().equals(value.toString()));
        }
    }
}
