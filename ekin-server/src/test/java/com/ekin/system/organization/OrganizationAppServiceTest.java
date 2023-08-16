//package com.ekin.system.organization;
//
//import com.cartisan.util.SnowflakeIdWorker;
//import com.ekin.system.organization.reponse.OrganizationDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.ekin.system.organization.OrganizationFixture.organizationOf;
//import static com.ekin.system.organization.OrganizationFixture.organizationParamOf;
//import static java.util.Collections.singletonList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//public class OrganizationAppServiceTest {
//    private OrganizationRepository repository;
//    private OrganizationAppService service;
//    private OrganizationParam organizationParam;
//    private Organization organization;
//    private SnowflakeIdWorker idWorker;
//
//    @Before
//    public void setUp() {
//        repository = mock(OrganizationRepository.class);
//        idWorker = mock(SnowflakeIdWorker.class);
//        service = new OrganizationAppService(repository, idWorker);
//
//        organizationParam = organizationParamOf();
//        organization = organizationOf();
//    }
//
//    @Test
//    public void should_get_organization_tree_list() {
//        // given
//        when(repository.findAll(any(Sort.class))).thenReturn(singletonList(organization));
//
//        // when
//        final List<OrganizationDto> organizationDtos = service.getOrganizationTreeList();
//
//        // then
//        assertThat(organizationDtos.size()).isEqualTo(1);
//        assertThat(organizationDtos.get(0).getName()).isEqualTo(organization.getName());
//    }
//
//    @Test
//    public void should_add_success() {
//        // when
//        service.addOrganization(organizationParam);
//
//        // then
//        verify(repository).save(any(Organization.class));
//    }
//
//    @Test
//    public void should_edit_success() {
//        // given
//        when(repository.findById(anyLong())).thenReturn(Optional.of(organization));
//
//        // when
//        service.editOrganization(1L, organizationParam);
//
//        // then
//        verify(repository).save(any(Organization.class));
//    }
//
//    @Test
//    public void should_remove_success() {
//        // when
//        service.removeOrganization(1L);
//
//        // then
//        verify(repository).deleteById(eq(1L));
//    }
//}