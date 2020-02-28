package com.ekin.system.resource.domain;

import com.cartisan.exceptions.CartisanException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceTest {

    public static final String NAME = "用户管理";
    public static final String CODE = "system:user";
    public static final String URL = "/system/users/**";
    public static final long CATEGORY_ID = 1L;

    private Resource resource;

    @Before
    public void setUp() {
        resource = new Resource(NAME, CODE, URL, CATEGORY_ID);
    }

    @Test
    public void when_code_is_null_then_throw_exception() {
        assertThatThrownBy(() -> new Resource(NAME, null, URL, CATEGORY_ID))
                .isInstanceOf(CartisanException.class)
                .hasMessage(Resource.ERR_CODE_NOT_EMPTY);
    }

    @Test
    public void when_code_is_empty_then_throw_exception() {
        assertThatThrownBy(() -> new Resource(NAME, null, URL, CATEGORY_ID))
                .isInstanceOf(CartisanException.class)
                .hasMessage(Resource.ERR_CODE_NOT_EMPTY);
    }

    @Test
    public void should_create_resource_success() {
        assertThat(resource.getName()).isEqualTo(NAME);
        assertThat(resource.getCode()).isEqualTo(CODE);
        assertThat(resource.getUrl()).isEqualTo(URL);
        assertThat(resource.getCategoryId()).isEqualTo(CATEGORY_ID);

        assertThat(resource.getDescription()).isEqualTo("");
        assertThat(resource.getSort()).isEqualTo(0);
    }

    @Test
    public void when_config_permission_code_is_null_then_throw_exception() {
        assertThatThrownBy(() -> resource.configPermission(null, URL))
                .isInstanceOf(CartisanException.class)
                .hasMessage(Resource.ERR_CODE_NOT_EMPTY);
    }

    @Test
    public void when_config_permission_code_is_empty_then_throw_exception() {
        assertThatThrownBy(() -> resource.configPermission("", URL))
                .isInstanceOf(CartisanException.class)
                .hasMessage(Resource.ERR_CODE_NOT_EMPTY);
    }

    @Test
    public void should_config_permission_success() {
        // when
        String newCode = "system:user-1";
        String newUrl = "/system/users/**/test";
        resource.configPermission(newCode, newUrl);

        // then
        assertThat(resource.getCode()).isEqualTo(newCode);
        assertThat(resource.getUrl()).isEqualTo(newUrl);
    }

    @Test
    public void should_describe_resource_success() {
        // when
        String newName = "产品管理-1";
        Long newCategoryId = 2L;
        String newDescription = "test";
        Integer newSort = 20;
        resource.describe(newName, newCategoryId, newDescription, newSort);

        // then
        assertThat(resource.getName()).isEqualTo(newName);
        assertThat(resource.getCategoryId()).isEqualTo(newCategoryId);
        assertThat(resource.getDescription()).isEqualTo(newDescription);
        assertThat(resource.getSort()).isEqualTo(newSort);
    }
}