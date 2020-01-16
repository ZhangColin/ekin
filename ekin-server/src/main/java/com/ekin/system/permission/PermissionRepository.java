package com.ekin.system.permission;

import com.cartisan.repositories.BaseRepository;

/**
 * @author colin
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    boolean existsByParentIdAndName(Long parentId, String name);
    boolean existsByParentIdAndNameAndIdNot(Long parentId, String name, Long departmentId);

    boolean existsByParentId(Long parentId);
}
