package com.ekin.system.role.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {

    private Role role;

    @Before
    public void setUp() throws Exception {
        role = new Role("admin", "super manager", 10);
    }

    @Test
    public void should_create_role_success() {
        assertThat(role.getName()).isEqualTo("admin");
        assertThat(role.getDescription()).isEqualTo("super manager");
        assertThat(role.getSort()).isEqualTo(10);
        assertThat(role.getStatus()).isEqualTo(1);
        assertThat(role.getMenus().size()).isEqualTo(0);
        assertThat(role.getResources().size()).isEqualTo(0);
    }

    @Test
    public void should_change_role_success() {
        // when
        role.change("admin1", "super manager 1", 20);

        // then
        assertThat(role.getName()).isEqualTo("admin1");
        assertThat(role.getDescription()).isEqualTo("super manager 1");
        assertThat(role.getSort()).isEqualTo(20);
    }

    @Test
    public void should_disable_role_success() {
        // when
        role.disable();

        // then
        assertThat(role.getStatus()).isEqualTo(0);
    }

    @Test
    public void should_enable_role_success() {
        // given
        role.disable();
        assertThat(role.getStatus()).isEqualTo(0);

        // when
        role.enable();

        // then
        assertThat(role.getStatus()).isEqualTo(1);
    }

    @Test
    public void should_assign_menus() {
        role.assignMenus(Stream.of(1L, 2L).collect(toList()));
        assertThat(role.getMenus().size()).isEqualTo(2);
        assertThat(role.getMenus().get(0).getMenuId()).isEqualTo(1L);
        assertThat(role.getMenus().get(1).getMenuId()).isEqualTo(2L);

        role.assignMenus(Stream.of(2L, 3L).collect(toList()));
        assertThat(role.getMenus().size()).isEqualTo(2);
        assertThat(role.getMenus().get(0).getMenuId()).isEqualTo(2L);
        assertThat(role.getMenus().get(1).getMenuId()).isEqualTo(3L);
    }

    @Test
    public void should_assign_resources() {
        role.assignResources(Stream.of(1L, 2L).collect(toList()));
        assertThat(role.getResources().size()).isEqualTo(2);
        assertThat(role.getResources().get(0).getResourceId()).isEqualTo(1L);
        assertThat(role.getResources().get(1).getResourceId()).isEqualTo(2L);

        role.assignResources(Stream.of(2L, 3L).collect(toList()));
        assertThat(role.getResources().size()).isEqualTo(2);
        assertThat(role.getResources().get(0).getResourceId()).isEqualTo(2L);
        assertThat(role.getResources().get(1).getResourceId()).isEqualTo(3L);
    }
}