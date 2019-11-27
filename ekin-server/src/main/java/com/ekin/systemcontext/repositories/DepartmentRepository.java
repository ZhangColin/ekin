package com.ekin.systemcontext.repositories;

import com.cartisan.common.repositories.BaseRepository;
import com.ekin.systemcontext.domains.Department;

/**
 * @author colin
 */
public interface DepartmentRepository extends BaseRepository<Department, Long> {
    boolean existsByParentIdAndName(Long parentId, String name);
    boolean existsByParentIdAndNameAndIdNot(Long parentId, String name, Long departmentId);

    boolean existsByParentId(Long parentId);
}
