package com.ekin.system.menu;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.ekin.system.menu.MenuFixture.menuOf;
import static com.ekin.system.menu.MenuFixture.menuParamOf;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MenuAppServiceTest {
    private MenuRepository repository;
    private MenuAppService service;
    private MenuParam menuParam;
    private Menu menu;

    @Before
    public void setUp() {
        repository = mock(MenuRepository.class);
        service = new MenuAppService(repository);

        menuParam = menuParamOf();
        menu = menuOf();
    }

    @Test
    public void should_get_menu_tree_list() {
        // given
        when(repository.findAll(any(Sort.class))).thenReturn(singletonList(menu));

        // when
        final List<MenuDto> menuDtos = service.getMenuTreeList();

        // then
        assertThat(menuDtos.size()).isEqualTo(1);
        assertThat(menuDtos.get(0).getName()).isEqualTo(menu.getName());
    }

    @Test
    public void should_add_success() {
        // when
        service.addMenu(menuParam);

        // then
        verify(repository).save(any(Menu.class));
    }

    @Test
    public void should_edit_success() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(menu));

        // when
        service.editMenu(1L, menuParam);

        // then
        verify(repository).save(any(Menu.class));
    }

    @Test
    public void should_remove_success() {
        // when
        service.removeMenu(1L);

        // then
        verify(repository).deleteById(eq(1L));
    }
}