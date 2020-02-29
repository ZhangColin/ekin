package com.ekin.system.role.response;

import com.ekin.system.role.domain.Role;
import org.junit.Test;

import static com.google.common.primitives.Longs.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleDetailConverterTest {

    @Test
    public void convert() {
        // given
        final Role role = new Role("admin");
        role.assignMenus(asList(1,2,3));
        role.assignResources(asList(1,2,3));

        // when
        final RoleDetailDto roleDetailDto = RoleDetailConverter.CONVERTER.convert(role);

        // then
        assertThat(roleDetailDto).isNotNull();
        assertThat(roleDetailDto.getName()).isEqualTo(role.getName());

        assertThat(roleDetailDto.getMenuIds().size()).isEqualTo(3);
        assertThat(roleDetailDto.getMenuIds().get(0)).isEqualTo("1");
        assertThat(roleDetailDto.getMenuIds().get(1)).isEqualTo("2");
        assertThat(roleDetailDto.getMenuIds().get(2)).isEqualTo("3");

        assertThat(roleDetailDto.getResourceIds().size()).isEqualTo(3);
        assertThat(roleDetailDto.getResourceIds().get(0)).isEqualTo("1");
        assertThat(roleDetailDto.getResourceIds().get(1)).isEqualTo("2");
        assertThat(roleDetailDto.getResourceIds().get(2)).isEqualTo("3");
    }
}