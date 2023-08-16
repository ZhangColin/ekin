package com.ekin.system.role;

import com.cartisan.constant.CodeMessage;
import com.cartisan.exception.CartisanException;
import com.ekin.system.role.domain.Role;
import com.ekin.system.role.request.RoleParam;
import com.ekin.system.role.response.RoleConverter;
import com.ekin.system.role.response.RoleDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.util.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class RoleAppService {
    public static final String ERR_NAME_EXISTS = "角色已存在。";

    private final RoleRepository repository;

    private final RoleConverter converter = RoleConverter.CONVERTER;
    public RoleAppService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<RoleDto> getRoleTreeList() {
        final List<RoleDto> roleDtos = converter.convert(
                repository.findAll());

        return RoleDto. buildRoleTreeList(roleDtos);
    }

    public RoleDto getRole(Long id) {
        final Role role = requirePresent(repository.findById(id));

        return converter.convert(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDto addRole(RoleParam roleParam) {
        if (repository.existsByName(roleParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Role role = new Role(roleParam.getParentId(), roleParam.getName());
        role.setRules(roleParam.getRules());
        role.setStatus(roleParam.getStatus());

        return converter.convert(repository.save(role));
    }

    @Transactional(rollbackOn = Exception.class)
    public RoleDto editRole(Long id, RoleParam roleParam) {
        if (repository.existsByNameAndIdNot(roleParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Role role = requirePresent(repository.findById(id));
        role.setName(roleParam.getName());
        role.setRules(roleParam.getRules());
        role.setStatus(roleParam.getStatus());

        return converter.convert(repository.save(role));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeRole(long id) {
        if (id == 1) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("不能删除超级管理员角色"));
        }
        repository.deleteById(id);
    }

}
