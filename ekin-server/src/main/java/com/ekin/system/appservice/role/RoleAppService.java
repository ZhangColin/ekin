package com.ekin.system.appservice.role;

import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.appservice.role.response.RoleDto;
import com.ekin.system.constant.SystemCodeMessage;
import com.ekin.system.domain.role.Role;
import com.ekin.system.appservice.role.response.RoleConverter;
import com.ekin.system.appservice.role.response.RoleInfo;
import com.ekin.system.appservice.role.request.RoleParam;
import com.ekin.system.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class RoleAppService {
    private final RoleRepository repository;
    private final SnowflakeIdWorker idWorker;
    private final RoleConverter converter;

    @Autowired
    public RoleAppService(RoleRepository repository, SnowflakeIdWorker idWorker, RoleConverter converter) {
        this.repository = repository;
        this.idWorker = idWorker;
        this.converter = converter;
    }

    public PageResult<RoleDto> searchRoles(String name, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);

        final Page<Role> searchResult = StringUtils.isBlank(name) ?
                repository.findAll(pageRequest) :
                repository.findByNameLike(name, pageRequest);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public List<RoleInfo> getAllRoles() {
        return repository.findAll().stream().map(RoleInfo::convertFrom).collect(toList());
    }

    public RoleDto getRole(Long id) {
        return repository.findById(id).map(converter::convert)
                .orElseThrow(()->new CartisanException(SystemCodeMessage.ROLE_NOT_EXIST));
    }

    @Transactional(rollbackOn = Exception.class)
    public void addRole(RoleParam roleParam) {
        if (repository.existsByName(roleParam.getName())) {
            throw new CartisanException(SystemCodeMessage.SAME_ROlE_NAME);
        }
        final Role role = new Role(idWorker.nextId(), roleParam.getName(), roleParam.getCode());
        role.setDescription(roleParam.getDescription());

        repository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editRole(Long id, RoleParam roleParam) {
        if (repository.existsByNameAndIdNot(roleParam.getName(), id)) {
            throw new CartisanException(SystemCodeMessage.SAME_ROlE_NAME);
        }
        final Role role = repository.findById(id).get();

        role.setName(roleParam.getName());
        role.setCode(roleParam.getCode());

        role.setDescription(roleParam.getDescription());

        repository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeRole(long id) {
        repository.deleteById(id);
    }


    public List<String> getRolePermissions(Long id) {
        return repository.findById(id).get().getPermissions().stream()
                .map(rolePermission->rolePermission.getPermissionId().toString()).collect(toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignPermissions(Long id, List<Long> permissionIds) {
        final Role role = repository.findById(id).get();
        role.assignPermissions(permissionIds);
        repository.save(role);
    }
}
