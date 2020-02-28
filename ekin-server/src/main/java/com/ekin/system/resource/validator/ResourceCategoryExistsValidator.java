package com.ekin.system.resource.validator;

import com.ekin.system.resource.application.ResourceCategoryAppService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author colin
 */
public class ResourceCategoryExistsValidator implements ConstraintValidator<ResourceCategoryExists, Long> {
    private final ResourceCategoryAppService resourceCategoryAppService;

    public ResourceCategoryExistsValidator(ResourceCategoryAppService resourceCategoryAppService) {
        this.resourceCategoryAppService = resourceCategoryAppService;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return resourceCategoryAppService.getAllResourceCategories().stream()
                .anyMatch(resourceCategoryDto -> resourceCategoryDto.getId().equals(value.toString()));
    }
}
