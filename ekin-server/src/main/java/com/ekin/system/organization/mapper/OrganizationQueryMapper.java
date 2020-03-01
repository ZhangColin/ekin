package com.ekin.system.organization.mapper;

import com.cartisan.dtos.TreeNode;

import java.util.List;

/**
 * @author colin
 */
public interface OrganizationQueryMapper {
    List<TreeNode> getDepartmentTreeNodes();
}
