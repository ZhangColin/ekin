package com.ekin.system.permission.mapper;

import com.cartisan.dtos.TreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author colin
 */
public interface PermissionQueryMapper {
    List<TreeNode> getPermissionTree();

    List<String> getPermissionCodesByRoleCodes(@Param("roleCodes") List<String> roleCodes);
}
