package com.ekin.system.menu;

import com.cartisan.repositories.BaseRepository;

import java.util.List;

/**
 * @author colin
 */
public interface MenuRepository extends BaseRepository<Menu, Long> {
    List<Menu> findByIdIn(List<Long> menuIds);
}
