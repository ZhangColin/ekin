package com.ekin.system.resource.repository;

import com.cartisan.repositories.BaseRepository;
import com.ekin.system.resource.domain.Resource;

/**
 * @author colin
 */
public interface ResourceRepository extends BaseRepository<Resource, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
