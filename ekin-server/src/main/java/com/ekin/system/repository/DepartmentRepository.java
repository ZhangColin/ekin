package com.ekin.system.repository;

import com.cartisan.repositories.BaseRepository;
import com.ekin.system.domain.department.Department;

/**
 * @author colin
 */
public interface DepartmentRepository extends BaseRepository<Department, Long> {
    boolean existsByParentIdAndName(Long parentId, String name);
    boolean existsByParentIdAndNameAndIdNot(Long parentId, String name, Long departmentId);

    boolean existsByParentId(Long parentId);
}
