package com.ekin.system.organization;

import com.ekin.system.organization.reponse.OrganizationTreeNode;
import org.junit.Test;

import java.util.List;

import static com.ekin.system.organization.OrganizationFixture.organizationDtoOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganizationTreeNodeTest {
    @Test
    public void buildOrganizationTreeList() {
        // given
        final OrganizationTreeNode organizationTreeNode = organizationDtoOf();

        final OrganizationTreeNode chileOrganization = new OrganizationTreeNode();
        chileOrganization.setName(organizationTreeNode.getName() + "-1");
        chileOrganization.setParentId(organizationTreeNode.getId());

        // when
        final List<OrganizationTreeNode> organizationTreeList = OrganizationTreeNode.buildOrganizationTreeList(asList(organizationTreeNode, chileOrganization));

        // then
        assertThat(organizationTreeList.size()).isEqualTo(1);
        assertThat(organizationTreeList.get(0).getName()).isEqualTo(organizationTreeNode.getName());

        assertThat(organizationTreeList.get(0).getChildOrganizations().size()).isEqualTo(1);
        assertThat(organizationTreeList.get(0).getChildOrganizations().get(0).getName()).isEqualTo(organizationTreeNode.getName() + "-1");
    }
}