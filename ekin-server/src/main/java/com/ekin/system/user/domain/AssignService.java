package com.ekin.system.user.domain;

import com.ekin.system.organization.OrganizationRepository;
import com.ekin.system.role.RoleRepository;
import com.ekin.system.role.domain.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class AssignService {
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;

    public AssignService(RoleRepository roleRepository,
                         OrganizationRepository organizationRepository) {
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
    }

    public void assignRoles(User user, List<Long> roleIds) {
        if (roleIds == null) {
            return;
        }
        final List<Long> ensureRoleIds = roleRepository.findAllById(roleIds)
                .stream().map(Role::getId).collect(toList());

        user.assignRoles(ensureRoleIds);
    }

    public void assignOrganization(final User user, Long organizationId) {
        if (organizationId == null) {
            return;
        }

        requirePresent(organizationRepository.findById(organizationId), "分配的组织不存在。");

        List<Long> ensureOrganizationIds = new ArrayList<>();
        ensureOrganizationIds.add(organizationId);
        user.assignOrganizations(ensureOrganizationIds);
    }
}
