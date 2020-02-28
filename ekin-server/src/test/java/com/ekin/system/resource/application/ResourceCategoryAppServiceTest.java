package com.ekin.system.resource.application;

import com.cartisan.exceptions.CartisanException;
import com.ekin.system.resource.domain.ResourceCategory;
import com.ekin.system.resource.repository.ResourceCategoryRepository;
import com.ekin.system.resource.request.ResourceCategoryParam;
import com.ekin.system.resource.response.ResourceCategoryDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ResourceCategoryAppServiceTest {
    private ResourceCategoryRepository repository;
    private ResourceCategoryAppService service;
    private ResourceCategoryParam resourceCategoryParam;
    private ResourceCategory resourceCategory;

    @Before
    public void setUp() {
        repository = mock(ResourceCategoryRepository.class);
        service = new ResourceCategoryAppService(repository);

        resourceCategoryParam = new ResourceCategoryParam();
        resourceCategoryParam.setName("系统管理");
        resourceCategoryParam.setSort(20);

        resourceCategory = new ResourceCategory(resourceCategoryParam.getName(),
                resourceCategoryParam.getSort());
    }

    @Test
    public void should_get_all_resource_categories() {
        // given
        when(repository.findAll(any(Sort.class))).thenReturn(singletonList(resourceCategory));

        // when
        final List<ResourceCategoryDto> resourceCategories = service.getAllResourceCategories();

        // then
        assertThat(resourceCategories.size()).isEqualTo(1);
        assertThat(resourceCategories.get(0).getId()).isEqualTo(resourceCategory.getId());
        assertThat(resourceCategories.get(0).getName()).isEqualTo(resourceCategory.getName());
        assertThat(resourceCategories.get(0).getSort()).isEqualTo(resourceCategory.getSort());
    }

    @Test
    public void when_add_name_already_exist_then_throw_exception() {
        // given
        when(repository.existsByName(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.addResourceCategory(resourceCategoryParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(ResourceCategoryAppService.ERR_NAME_EXISTS);
    }

    @Test
    public void should_add_success() {
        // given
        when(repository.existsByName(anyString())).thenReturn(false);

        // when
        service.addResourceCategory(resourceCategoryParam);

        // then
        verify(repository).save(any(ResourceCategory.class));
    }

    @Test
    public void when_edit_name_already_exist_then_throw_exception() {
        // given
        when(repository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.editResourceCategory(1L, resourceCategoryParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(ResourceCategoryAppService.ERR_NAME_EXISTS);
    }

    @Test
    public void should_edit_success() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(resourceCategory));
        when(repository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);

        // when
        service.editResourceCategory(1L, resourceCategoryParam);

        // then
        verify(repository).save(any(ResourceCategory.class));
    }

    @Test
    public void should_remove_success() {
        // when
        service.removeResourceCategory(1L);

        // then
        verify(repository).deleteById(eq(1L));
    }
}