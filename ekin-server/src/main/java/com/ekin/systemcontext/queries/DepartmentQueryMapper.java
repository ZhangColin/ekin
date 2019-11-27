package com.ekin.systemcontext.queries;

import com.ekin.systemcontext.dtos.TreeNode;

import java.util.List;

/**
 * @author colin
 */
public interface DepartmentQueryMapper {
    List<TreeNode> getDepartmentTreeNodes();
}
