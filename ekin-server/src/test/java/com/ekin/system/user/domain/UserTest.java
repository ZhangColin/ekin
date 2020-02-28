package com.ekin.system.user.domain;


import org.junit.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    public void should_allow_phone_and_email_is_null() {
        // when
        final User user = new User(1L, "colin", null, null, "123456", "文野");

        // then
        assertThat(user.getPhone()).isEqualTo("");
        assertThat(user.getEmail()).isEqualTo("");
    }

    @Test
    public void should_assign_roles() {
        // given
        final User user = new User(1L, "colin", "13962830605", "stwyhm@126.com", "123456", "文野");

        user.assignRoles(Stream.of(1L, 2L).collect(toList()));
        assertThat(user.getRoles().size()).isEqualTo(2);
        assertThat(user.getRoles().get(0).getRoleId()).isEqualTo(1L);
        assertThat(user.getRoles().get(1).getRoleId()).isEqualTo(2L);

        user.assignRoles(Stream.of(2L, 3L).collect(toList()));
        assertThat(user.getRoles().size()).isEqualTo(2);
        assertThat(user.getRoles().get(0).getRoleId()).isEqualTo(2L);
        assertThat(user.getRoles().get(1).getRoleId()).isEqualTo(3L);
    }

    @Test
    public void should_assign_organizations() {
        // given
        final User user = new User(1L, "colin", "13962830605", "stwyhm@126.com", "123456", "文野");

        user.assignOrganizations(Stream.of(1L,2L).collect(toList()));
        assertThat(user.getOrganizations().size()).isEqualTo(2);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
        assertThat(user.getOrganizations().get(1).getOrganizationId()).isEqualTo(2L);

        user.assignOrganizations(Stream.of(2L,3L).collect(toList()));
        assertThat(user.getOrganizations().size()).isEqualTo(2);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(2L);
        assertThat(user.getOrganizations().get(1).getOrganizationId()).isEqualTo(3L);
    }
}
