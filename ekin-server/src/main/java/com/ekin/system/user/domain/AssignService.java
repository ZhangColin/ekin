package com.ekin.system.user.domain;

import com.ekin.system.role.RoleRepository;
import com.ekin.system.role.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class AssignService {
    private final RoleRepository roleRepository;
//    private final OrganizationRepository organizationRepository;

    public AssignService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void assignRoles(User user, List<Long> roleIds) {
        if (roleIds == null) {
            return;
        }
        final List<Long> ensureRoleIds = roleRepository.findAllById(roleIds)
                .stream().map(Role::getId).collect(toList());

        user.assignRoles(ensureRoleIds);
    }

    public void assignOrganization(final User user, List<Long> organizationIds) {
        if (organizationIds == null) {
            return;
        }
//        List<Long> ensureOrganizationIds = organizationRepository.findAllById(organizationIds)
//                .stream().map(Organization::getId).collect(toList());
//        user.assignOrganizations(ensureOrganizationIds);
    }
}
