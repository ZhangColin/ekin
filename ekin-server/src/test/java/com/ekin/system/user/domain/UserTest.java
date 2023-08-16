//package com.ekin.system.user.domain;
//
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class UserTest {
//
//    private User user;
//
//    @Before
//    public void setUp() throws Exception {
//        user = new User(1L, "colin", "13962830605", "stwyhm@126.com", "123456", "文野");
//    }
//
//    @Test
//    public void should_allow_phone_and_email_is_null() {
//        // when
//        final User user = new User(1L, "colin", null, null, "123456", "文野");
//
//        // then
//        assertThat(user.getPhone()).isEqualTo("");
//        assertThat(user.getEmail()).isEqualTo("");
//    }
//
//    @Test
//    public void should_fill_profile() {
//        // given
//        final LocalDate birthday = LocalDate.of(2009, 10, 4);
//        final Gender gender = Gender.FEMALE;
//
//        // when
//        user.profile(birthday, gender);
//
//        // then
//        assertThat(user.getBirthday()).isEqualTo(birthday);
//        assertThat(user.getGender()).isEqualTo(gender);
//    }
//
//    @Test
//    public void should_assign_roles() {
//        // when
//        user.assignRoles(Stream.of(1L, 2L).collect(toList()));
//
//        // then
//        assertThat(user.getRoles().size()).isEqualTo(2);
//        assertThat(user.getRoles().get(0).getRoleId()).isEqualTo(1L);
//        assertThat(user.getRoles().get(1).getRoleId()).isEqualTo(2L);
//
//        // when
//        user.assignRoles(Stream.of(2L, 3L).collect(toList()));
//
//        // then
//        assertThat(user.getRoles().size()).isEqualTo(2);
//        assertThat(user.getRoles().get(0).getRoleId()).isEqualTo(2L);
//        assertThat(user.getRoles().get(1).getRoleId()).isEqualTo(3L);
//    }
//
//    @Test
//    public void should_assign_organizations() {
//        // when
//        user.assignOrganizations(Stream.of(1L,2L).collect(toList()));
//
//        // then
//        assertThat(user.getOrganizations().size()).isEqualTo(2);
//        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
//        assertThat(user.getOrganizations().get(1).getOrganizationId()).isEqualTo(2L);
//
//        // when
//        user.assignOrganizations(Stream.of(2L,3L).collect(toList()));
//
//        // then
//        assertThat(user.getOrganizations().size()).isEqualTo(2);
//        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(2L);
//        assertThat(user.getOrganizations().get(1).getOrganizationId()).isEqualTo(3L);
//    }
//}
