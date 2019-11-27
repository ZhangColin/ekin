package com.ekin.systemcontext.repositories;

import com.cartisan.common.repositories.BaseRepository;
import com.ekin.systemcontext.domains.Role;
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
