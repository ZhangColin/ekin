package com.ekin.system.user.mapper;


import com.ekin.system.menu.MenuDto;

import java.util.List;

/**
 * @author colin
 */
public interface UserQueryMapper {
    List<String> getUserAuthorities(Long userId);

    List<MenuDto> getUserMenus(Long userId);
}
