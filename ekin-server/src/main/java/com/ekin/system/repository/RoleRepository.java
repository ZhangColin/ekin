package com.ekin.system.repository;

import com.cartisan.repositories.BaseRepository;
import com.ekin.system.domain.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author colin
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    Page<Role> findByNameLike(String name, Pageable pageable);

}
