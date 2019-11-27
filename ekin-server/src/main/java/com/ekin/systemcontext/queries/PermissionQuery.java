//package com.ekin.systemcontext.queries;
//
//import com.ekin.systemcontext.dtos.TreeNode;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @author colin
// */
//@Service
//public class PermissionQuery {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List<TreeNode> getPermissionTree() {
//        final String sql = "select id, name, parent_id as parentId from sys_permissions order by sort asc";
//
//        final List<TreeNode> nodes = jdbcTemplate.query(sql,
//                new BeanPropertyRowMapper<>(TreeNode.class));
//        return TreeNode.buildTree(nodes);
//    }
//
//    public List<String> getPermissionCodesByRoleCodes(List<String> roleCodes) {
//        final String sql = "select p.`code` from sys_roles as r \n" +
//                "\tinner JOIN sys_role_permissions as rp on r.id=rp.role_id\n" +
//                "\tinner join sys_permissions as p on p.id=rp.permission_id\n" +
//                "where r.`code` in ('"+ StringUtils.join(roleCodes, "','") +"');";
//
//        return jdbcTemplate.queryForList(sql, String.class);
//    }
//}
