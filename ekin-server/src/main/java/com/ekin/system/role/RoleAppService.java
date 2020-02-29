package com.ekin.system.role;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.system.role.domain.Role;
import com.ekin.system.role.request.RoleParam;
import com.ekin.system.role.request.RoleQuery;
import com.ekin.system.role.response.RoleConverter;
import com.ekin.system.role.response.RoleDetailConverter;
import com.ekin.system.role.response.RoleDetailDto;
import com.ekin.system.role.response.RoleDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class RoleAppService {
    public static final String ERR_NAME_EXISTS = "角色已存在。";

    private final RoleRepository repository;
    private final RoleConverter roleConverter = RoleConverter.CONVERTER;
    private final RoleDetailConverter roleDetailConverter = RoleDetailConverter.CONVERTER;

    @Autowired
    public RoleAppService(RoleRepository repository) {
        this.repository = repository;
    }

    public PageResult<RoleDto> searchRoles(@NonNull RoleQuery roleQuery, @NonNull Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Direction.ASC, "sort"));
        final Page<Role> searchResult = repository.findAll(querySpecification(roleQuery), pageable);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                roleConverter.convert(searchResult.getContent()));
    }

    public List<RoleDto> getAllEnableRoles() {
        final RoleQuery roleQuery = new RoleQuery();
        roleQuery.setStatus(1);

        return roleConverter.convert(repository.findAll(querySpecification(roleQuery),
                Sort.by(Sort.Direction.ASC, "sort")));
    }

    public RoleDetailDto getRole(Long id) {
        final Role role = requirePresent(repository.findById(id));

        return roleDetailConverter.convert(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDetailDto addRole(RoleParam roleParam) {
        if (repository.existsByName(roleParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Role role = new Role(roleParam.getName());
        role.describe(roleParam.getName(), roleParam.getDescription(), role.getSort());

        return roleDetailConverter.convert(repository.save(role));
    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDetailDto editRole(Long id, RoleParam roleParam) {
        if (repository.existsByNameAndIdNot(roleParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Role role = requirePresent(repository.findById(id));

        role.describe(roleParam.getName(), roleParam.getDescription(), roleParam.getSort());

        return roleDetailConverter.convert(repository.save(role));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeRole(long id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignMenus(Long id, List<Long> menuIds) {
        final Role role = requirePresent(repository.findById(id));
        role.assignMenus(menuIds);
        repository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignResources(Long id, List<Long> resourceIds) {
        final Role role = requirePresent(repository.findById(id));
        role.assignResources(resourceIds);
        repository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public void enable(Long id) {
        final Role role = requirePresent(repository.findById(id));
        role.enable();
        repository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long id) {
        final Role role = requirePresent(repository.findById(id));
        role.disable();
        repository.save(role);
    }
}
