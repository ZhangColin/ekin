package com.ekin.system.resource.response;

import com.cartisan.CartisanContext;
import com.cartisan.exception.CartisanException;
import com.ekin.system.resource.ResourceCategoryFixture;
import com.ekin.system.resource.ResourceFixture;
import com.ekin.system.resource.application.ResourceCategoryAppService;
import com.ekin.system.resource.domain.Resource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceConverterTest {
    private Resource resource;
    private ResourceCategoryDto resourceCategoryDto;

    private ApplicationContext applicationContext;
    private ResourceCategoryAppService resourceCategoryAppService;
    private ResourceConverter converter;

    @Before
    public void setUp() {
        converter = ResourceConverter.CONVERTER;

        resource = ResourceFixture.resourceOf();
        resourceCategoryDto = ResourceCategoryFixture.categoryDtoOf();

        resourceCategoryAppService = mock(ResourceCategoryAppService.class);
        when(resourceCategoryAppService.getAllResourceCategories())
                .thenReturn(singletonList(resourceCategoryDto));

        applicationContext = mock(ApplicationContext.class);
        when(applicationContext.getBean(ResourceCategoryAppService.class))
                .thenReturn(resourceCategoryAppService);
        CartisanContext.setAppContext(applicationContext);
    }

    @Test
    public void when_not_matcher_category_then_throw_exception() {
        // given
        resource.describe(resource.getName(), 2L, resource.getDescription(), resource.getSort());

        // then
        assertThatThrownBy(() -> converter.convert(resource))
                .isInstanceOf(CartisanException.class)
                .hasMessage(ResourceConverter.ERR_CATEGORY_NOT_EXISTS);
    }

    @Test
    public void should_convert_single_object() {
        // when
        final ResourceDto resourceDto = converter.convert(resource);

        // then
        assertThat(resourceDto.getName()).isEqualTo(resource.getName());
        assertThat(resourceDto.getCode()).isEqualTo(resource.getCode());
        assertThat(resourceDto.getUrl()).isEqualTo(resource.getUrl());
        assertThat(resourceDto.getDescription()).isEqualTo(resource.getDescription());
        assertThat(resourceDto.getSort()).isEqualTo(resource.getSort());

        assertThat(resourceDto.getCategory().getId()).isEqualTo(resourceCategoryDto.getId());
        assertThat(resourceDto.getCategory().getName()).isEqualTo(resourceCategoryDto.getName());
    }

    @Test
    public void should_convert_list() {
        // when
        final List<ResourceDto> resourceDtos = converter.convert(singletonList(resource));

        // then
        assertThat(resourceDtos.size()).isEqualTo(1);

        assertThat(resourceDtos.get(0).getName()).isEqualTo(resource.getName());
        assertThat(resourceDtos.get(0).getCode()).isEqualTo(resource.getCode());
        assertThat(resourceDtos.get(0).getUrl()).isEqualTo(resource.getUrl());
        assertThat(resourceDtos.get(0).getDescription()).isEqualTo(resource.getDescription());
        assertThat(resourceDtos.get(0).getSort()).isEqualTo(resource.getSort());

        assertThat(resourceDtos.get(0).getCategory().getId()).isEqualTo(resourceCategoryDto.getId());
        assertThat(resourceDtos.get(0).getCategory().getName()).isEqualTo(resourceCategoryDto.getName());
    }
}