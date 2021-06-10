package com.ekin.system.user.mapper;


import com.ekin.system.menu.MenuDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author colin
 */
public interface UserQueryMapper {
    List<String> getUserAuthorities(@Param(value="userId") Long userId);

    List<MenuDto> getUserMenus(@Param(value="userId") Long userId);
}
