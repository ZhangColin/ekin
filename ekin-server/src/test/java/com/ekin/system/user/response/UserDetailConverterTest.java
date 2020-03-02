package com.ekin.system.user.response;

import com.cartisan.CartisanContext;
import com.ekin.system.organization.OrganizationAppService;
import com.ekin.system.organization.reponse.OrganizationDto;
import com.ekin.system.role.RoleAppService;
import com.ekin.system.role.response.RoleDto;
import com.ekin.system.user.UserFixture;
import com.ekin.system.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static com.google.common.primitives.Longs.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailConverterTest {
    private User user;
    private RoleDto roleDto;
    private OrganizationDto organizationDto;

    private UserDetailConverter converter;

    @Before
    public void setUp() {
        converter = UserDetailConverter.CONVERTER;

        user = UserFixture.userOf();

        roleDto = new RoleDto();
        roleDto.setId("1");
        roleDto.setName("admin");

        user.assignRoles(asList(1L));
        user.assignOrganizations(asList(1L));

        RoleAppService roleAppService = mock(RoleAppService.class);
        when(roleAppService.getAllEnableRoles())
                .thenReturn(singletonList(roleDto));

        organizationDto = new OrganizationDto();
        organizationDto.setId("1");
        organizationDto.setName("Cartisan");

        OrganizationAppService organizationAppService = mock(OrganizationAppService.class);
        when(organizationAppService.getAllOrganizations()).thenReturn(singletonList(organizationDto));

        ApplicationContext applicationContext = mock(ApplicationContext.class);
        when(applicationContext.getBean(RoleAppService.class))
                .thenReturn(roleAppService);
        when(applicationContext.getBean(OrganizationAppService.class))
                .thenReturn(organizationAppService);
        CartisanContext.setAppContext(applicationContext);
    }

    @Test
    public void should_convert_single_object() {
        // when
        final UserDetailDto userDetailDto = converter.convert(user);

        // then
        assertThat(userDetailDto.getRoles().size()).isEqualTo(1);
        assertThat(userDetailDto.getRoles().get(0)).isEqualTo(roleDto);

        assertThat(userDetailDto.getOrganization()).isEqualTo(organizationDto);
    }

    @Test
    public void should_convert_list() {
        // when
        final List<UserDetailDto> userDetailDtos = converter.convert(singletonList(user));

        // then
        assertThat(userDetailDtos.size()).isEqualTo(1);

        assertThat(userDetailDtos.get(0).getRoles().size()).isEqualTo(1);
        assertThat(userDetailDtos.get(0).getRoles().get(0)).isEqualTo(roleDto);

        assertThat(userDetailDtos.get(0).getOrganization()).isEqualTo(organizationDto);
    }
}