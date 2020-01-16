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

    public void assignRoles(Long userId, List<String> roleCodes) {
        final User user = requirePresent(this.userRepository.findById(userId));

        final List<String> ensureRoleCodes = roleRepository.findByCodeIn(roleCodes)
                .stream().map(Role::getCode).collect(toList());

        user.assignRoles(ensureRoleCodes);

        this.userRepository.save(user);
    }

    public void assignDepartments(Long userId, List<Long> departmentIds) {
        final User user = requirePresent(this.userRepository.findById(userId));

        final List<Long> ensureDepartmentIds = departmentRepository.findByIdIn(departmentIds)
                .stream().map(Department::getId).collect(toList());

        user.assignDepartments(ensureDepartmentIds);

        this.userRepository.save(user);
    }
}
