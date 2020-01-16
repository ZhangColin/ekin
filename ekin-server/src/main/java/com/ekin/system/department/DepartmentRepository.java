package com.ekin.system.department;

import com.cartisan.repositories.BaseRepository;

import java.util.List;

/**
 * @author colin
 */
public interface DepartmentRepository extends BaseRepository<Department, Long> {
    boolean existsByParentIdAndName(Long parentId, String name);
    boolean existsByParentIdAndNameAndIdNot(Long parentId, String name, Long departmentId);

    boolean existsByParentId(Long parentId);

    List<Department> findByIdIn(List<Long> departmentIds);
}
