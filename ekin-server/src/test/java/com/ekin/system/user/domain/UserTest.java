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
    public void should_assign_role_codes() {
        // given
        final User user = new User(1L, "colin", "13962830605", "stwyhm@126.com", "123456", "文野");

        user.assignRoles(Stream.of("admin1", "admin2").collect(toList()));
        assertThat(user.getRoles().size()).isEqualTo(2);
        assertThat(user.getRoles().get(0).getRoleCode()).isEqualTo("admin1");
        assertThat(user.getRoles().get(1).getRoleCode()).isEqualTo("admin2");

        user.assignRoles(Stream.of("admin2", "admin3").collect(toList()));
        assertThat(user.getRoles().size()).isEqualTo(2);
        assertThat(user.getRoles().get(0).getRoleCode()).isEqualTo("admin2");
        assertThat(user.getRoles().get(1).getRoleCode()).isEqualTo("admin3");
    }

    @Test
    public void should_assign_department_ids() {
        // given
        final User user = new User(1L, "colin", "13962830605", "stwyhm@126.com", "123456", "文野");

        user.assignDepartments(Stream.of(1L,2L).collect(toList()));
        assertThat(user.getDepartments().size()).isEqualTo(2);
        assertThat(user.getDepartments().get(0).getDepartmentId()).isEqualTo(1L);
        assertThat(user.getDepartments().get(1).getDepartmentId()).isEqualTo(2L);

        user.assignDepartments(Stream.of(2L,3L).collect(toList()));
        assertThat(user.getDepartments().size()).isEqualTo(2);
        assertThat(user.getDepartments().get(0).getDepartmentId()).isEqualTo(2L);
        assertThat(user.getDepartments().get(1).getDepartmentId()).isEqualTo(3L);
    }
}
