package com.ekin.system.user.domain;

import com.ekin.system.department.Department;
import com.ekin.system.department.DepartmentRepository;
import com.ekin.system.role.RoleRepository;
import com.ekin.system.role.domain.Role;
import com.ekin.system.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class AssignService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public AssignService(UserRepository userRepository,
                         RoleRepository roleRepository,
                         DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    public void assignRoles(Long userId, List<Long> roleIds) {
        final User user = requirePresent(this.userRepository.findById(userId));

        final List<Long> ensureRoleIds = roleRepository.findAllById(roleIds)
                .stream().map(Role::getId).collect(toList());

        user.assignRoles(ensureRoleIds);

        this.userRepository.save(user);
    }

    public void assignOrganizations(Long userId, List<Long> organizationIds) {
        final User user = requirePresent(this.userRepository.findById(userId));

        final List<Long> ensureOrganizationIds = departmentRepository.findByIdIn(organizationIds)
                .stream().map(Department::getId).collect(toList());

        user.assignOrganizations(ensureOrganizationIds);

        this.userRepository.save(user);
    }
}
