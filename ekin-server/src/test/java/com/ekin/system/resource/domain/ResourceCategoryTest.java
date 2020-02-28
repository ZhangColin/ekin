package com.ekin.system.resource.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceCategoryTest {
    public static final String NAME = "系统管理";
    public static final int SORT = 10;
    private ResourceCategory resourceCategory;

    @Before
    public void setUp() {
        resourceCategory = new ResourceCategory(NAME, SORT);
    }

    @Test
    public void should_create_resource_category_success() {
        assertThat(resourceCategory.getName()).isEqualTo(NAME);
        assertThat(resourceCategory.getSort()).isEqualTo(SORT);
    }

    @Test
    public void should_describe_resource_category_success() {
        // when
        final String newCategoryName = "产品管理";
        final int newSort = 20;

        resourceCategory.describe(newCategoryName, newSort);

        // then
        assertThat(resourceCategory.getName()).isEqualTo(newCategoryName);
        assertThat(resourceCategory.getSort()).isEqualTo(newSort);
    }
}