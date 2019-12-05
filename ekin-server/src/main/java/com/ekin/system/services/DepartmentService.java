package com.ekin.system.services;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.constants.SystemCodeMessage;
import com.ekin.system.domains.Department;
import com.ekin.system.dtos.DepartmentDto;
import com.ekin.system.params.DepartmentParam;
import com.ekin.system.queries.UserQueryMapper;
import com.ekin.system.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author colin
 */
@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    private final UserQueryMapper userQueryMapper;
    private final SnowflakeIdWorker idWorker;

    @Autowired
    public DepartmentService(DepartmentRepository repository, UserQueryMapper userQueryMapper, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.userQueryMapper = userQueryMapper;
        this.idWorker = idWorker;
    }

    public List<DepartmentDto> getDepartmentList() {
        final List<Department> departments = repository.findAll(Sort.by(Sort.Direction.ASC, "sort"));

        return DepartmentDto.buildDepartmentTreeList(departments);
    }


    @Transactional(rollbackOn = Exception.class)
    public void addDepartment(DepartmentParam departmentParam) {
        if (repository.existsByParentIdAndName(departmentParam.getParentId(), departmentParam.getName())) {
            throw new CartisanException(SystemCodeMessage.SAME_DEPARTMENT_NAME);
        }

        final Department department = new Department(
                idWorker.nextId(),
                departmentParam.getParentId(),
                departmentParam.getName());

        department.setDescription(departmentParam.getDescription());
        department.setSort(departmentParam.getSort());


        repository.save(department);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editDepartment(Long id, DepartmentParam departmentParam) {
        if (repository.existsByParentIdAndNameAndIdNot(departmentParam.getParentId(), departmentParam.getName(), id)) {
            throw new CartisanException(SystemCodeMessage.SAME_DEPARTMENT_NAME);
        }

        final Optional<Department> departmentOptional = repository.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new CartisanException(SystemCodeMessage.DEPARTMENT_NOT_EXIST);
        }

        final Department department = departmentOptional.get();
        department.setParentId(departmentParam.getParentId());
        department.setName(departmentParam.getName());

        department.setDescription(departmentParam.getDescription());
        department.setSort(departmentParam.getSort());

        repository.save(department);
    }


    @Transactional(rollbackOn = Exception.class)
    public void removeDepartment(long id) {
        final Optional<Department> departmentOptional = repository.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new CartisanException(SystemCodeMessage.DEPARTMENT_NOT_EXIST);
        }

        if (repository.existsByParentId(id)) {
            throw new CartisanException(SystemCodeMessage.HAS_CHILD_DEPARTMENT);
        }

        if (userQueryMapper.existsUserInDepartment(id)) {
            throw new CartisanException(SystemCodeMessage.HAS_USER_IN_DEPARTMENT);
        }

        repository.deleteById(id);
    }
}
