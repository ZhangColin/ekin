//package com.ekin.system.organization;
//
//import com.ekin.system.organization.reponse.OrganizationDto;
//import org.junit.Test;
//
//import java.util.List;
//
//import static com.ekin.system.organization.OrganizationFixture.organizationDtoOf;
//import static java.util.Arrays.asList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class OrganizationDtoTest {
//    @Test
//    public void buildOrganizationTreeList() {
//        // given
//        final OrganizationDto organizationDto = organizationDtoOf();
//
//        final OrganizationDto chileOrganization = new OrganizationDto();
//        chileOrganization.setName(organizationDto.getName() + "-1");
//        chileOrganization.setParentId(organizationDto.getId());
//
//        // when
//        final List<OrganizationDto> organizationTreeList = OrganizationDto.buildOrganizationTreeList(asList(organizationDto, chileOrganization));
//
//        // then
//        assertThat(organizationTreeList.size()).isEqualTo(1);
//        assertThat(organizationTreeList.get(0).getName()).isEqualTo(organizationDto.getName());
//
//        assertThat(organizationTreeList.get(0).getChildOrganizations().size()).isEqualTo(1);
//        assertThat(organizationTreeList.get(0).getChildOrganizations().get(0).getName()).isEqualTo(organizationDto.getName() + "-1");
//    }
//}