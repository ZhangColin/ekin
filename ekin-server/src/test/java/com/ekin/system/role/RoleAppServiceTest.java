package com.ekin.system.role;

import com.cartisan.exceptions.CartisanException;
import com.ekin.system.menu.MenuRepository;
import com.ekin.system.resource.repository.ResourceRepository;
import com.ekin.system.role.request.RoleParam;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoleAppServiceTest {
    private RoleRepository repository;
    private MenuRepository menuRepository;
    private ResourceRepository resourceRepository;
    private RoleAppService service;
    private RoleParam roleParam;

    @Before
    public void setUp() {
        repository = mock(RoleRepository.class);
        menuRepository = mock(MenuRepository.class);
        resourceRepository = mock(ResourceRepository.class);

        service = new RoleAppService(repository, menuRepository, resourceRepository);
        roleParam = new RoleParam();
        roleParam.setName("用户管理");
        roleParam.setDescription("用户管理描述");
        roleParam.setSort(10);
    }

    @Test
    public void when_add_name_already_exist_then_throw_exception() {
        // given
        when(repository.existsByName(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.addRole(roleParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(RoleAppService.ERR_NAME_EXISTS);
    }

    @Test
    public void when_edit_name_already_exist_then_throw_exception() {
        // given
        when(repository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.editRole(1L, roleParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(RoleAppService.ERR_NAME_EXISTS);
    }
}