package com.ekin.system.user.domain;

import com.ekin.system.organization.Organization;
import com.ekin.system.organization.OrganizationRepository;
import com.ekin.system.role.RoleRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.ekin.system.user.UserFixture.userOf;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignServiceTest {

    private User user;
    private OrganizationRepository organizationRepository;
    private Organization organization;
    private AssignService assignService;

    @Before
    public void setUp() throws Exception {
        user = userOf();
        organizationRepository = mock(OrganizationRepository.class);
        organization = new Organization(1L, 0L, "Cartisan");
        assignService = new AssignService(mock(RoleRepository.class), organizationRepository);

        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organization));
    }

    @Test
    public void should_assign_organization_success() {
        // when
        assignService.assignOrganization(user, 1L);

        // then
        assertThat(user.getOrganizations().size()).isEqualTo(1);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
    }


    @Test
    public void when_organization_already_in_user_organizations_then_no_error() {
        // given
        user.assignOrganizations(singletonList(organization.getId()));

        // when
        assignService.assignOrganization(user, 1L);

        // then
        assertThat(user.getOrganizations().size()).isEqualTo(1);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
    }
}