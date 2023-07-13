package com.ekin.system.resource.repository;

import com.cartisan.repository.BaseRepository;
import com.ekin.system.resource.domain.ResourceCategory;

/**
 * @author colin
 */
public interface ResourceCategoryRepository extends BaseRepository<ResourceCategory, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
