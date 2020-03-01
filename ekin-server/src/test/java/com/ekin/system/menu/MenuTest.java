package com.ekin.system.menu;

import org.junit.Before;
import org.junit.Test;

import static com.ekin.system.menu.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {
    private Menu menu;

    @Before
    public void setUp() {
        menu = menuOf();
    }

    @Test
    public void should_create_menu_success() {
        assertThat(menu.getParentId()).isEqualTo(PARENT_ID);
        assertThat(menu.getTitle()).isEqualTo(TITLE);
        assertThat(menu.getIcon()).isEqualTo(ICON);
        assertThat(menu.getHidden()).isEqualTo(HIDDEN);
        assertThat(menu.getLevel()).isEqualTo(LEVEL);
        assertThat(menu.getSort()).isEqualTo(SORT);
    }

    @Test
    public void should_describe_menu_success() {
        // when
        menu.change(PARENT_ID + 1, TITLE + 1, NAME + 1, ICON + 1,
                !HIDDEN, LEVEL + 1, SORT + 1);

        // then
        assertThat(menu.getParentId()).isEqualTo(PARENT_ID + 1);
        assertThat(menu.getTitle()).isEqualTo(TITLE + 1);
        assertThat(menu.getIcon()).isEqualTo(ICON + 1);
        assertThat(menu.getHidden()).isEqualTo(!HIDDEN);
        assertThat(menu.getLevel()).isEqualTo(LEVEL + 1);
        assertThat(menu.getSort()).isEqualTo(SORT + 1);
    }
}