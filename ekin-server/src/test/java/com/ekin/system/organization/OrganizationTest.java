//package com.ekin.system.organization;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static com.ekin.system.organization.OrganizationFixture.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class OrganizationTest {
//    private Organization organization;
//
//    @Before
//    public void setUp() {
//        organization = organizationOf();
//    }
//
//    @Test
//    public void should_create_organization_success() {
//        assertThat(organization.getParentId()).isEqualTo(PARENT_ID);
//        assertThat(organization.getName()).isEqualTo(NAME);
//        assertThat(organization.getDescription()).isEqualTo(DESCRIPTION);
//        assertThat(organization.getSort()).isEqualTo(SORT);
//    }
//
//    @Test
//    public void should_describe_organization_success() {
//        // when
//        organization.describe(DESCRIPTION + 1, SORT + 1);
//
//        // then
//        assertThat(organization.getDescription()).isEqualTo(DESCRIPTION + 1);
//        assertThat(organization.getSort()).isEqualTo(SORT + 1);
//    }
//}