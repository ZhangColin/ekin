package com.ekin.system.queries;

import com.ekin.system.dtos.TreeNode;

import java.util.List;

/**
 * @author colin
 */
public interface DepartmentQueryMapper {
    List<TreeNode> getDepartmentTreeNodes();
}
