package com.ekin.system.department.mapper;

import com.cartisan.dtos.TreeNode;

import java.util.List;

/**
 * @author colin
 */
public interface DepartmentQueryMapper {
    List<TreeNode> getDepartmentTreeNodes();
}
