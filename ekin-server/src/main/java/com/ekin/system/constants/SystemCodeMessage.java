package com.ekin.system.constants;


import com.cartisan.constants.CodeMessage;

/**
 * @author colin
 */
public class SystemCodeMessage extends CodeMessage {

    protected SystemCodeMessage(Integer code, String message) {
        super(code, message);
    }

    /**
     * 登录
     */
    public static CodeMessage ERROR_USERNAME_OR_PASSWORD = new SystemCodeMessage(50101, "用户名或密码不正确");


    /**
     * 部门
     */
    public static CodeMessage DEPARTMENT_NOT_EXIST = new SystemCodeMessage(51001, "部门不存在");
    public static CodeMessage SAME_DEPARTMENT_NAME = new SystemCodeMessage(51002, "同一层级下存在相同名称的部门");
    public static CodeMessage HAS_CHILD_DEPARTMENT = new SystemCodeMessage(51050, "当前部门下面有子部门，无法删除");
    public static CodeMessage HAS_USER_IN_DEPARTMENT = new SystemCodeMessage(51051, "当前部门下面有用户，无法删除");

    /**
     * 用户
     */
    public static CodeMessage USER_NOT_EXIST = new SystemCodeMessage(52001, "用户不存在");
    public static CodeMessage USERNAME_EXIST = new SystemCodeMessage(52002, "用户名已被占用");
    public static CodeMessage EMAIL_EXIST = new SystemCodeMessage(52002, "邮箱已被占用");
    public static CodeMessage PHONE_EXIST = new SystemCodeMessage(52002, "电话已被占用");

    /**
     * 角色
     */
    public static CodeMessage ROLE_NOT_EXIST = new SystemCodeMessage(53001, "角色不存在");
    public static CodeMessage SAME_ROlE_NAME = new SystemCodeMessage(53002, "角色名称已经存在");
    public static CodeMessage HAS_USER_IS_ROLE = new SystemCodeMessage(53051, "当前角色分配给了用户，无法删除");

    /**
     * 权限
     */
    public static CodeMessage PERMISSION_NOT_EXIST = new SystemCodeMessage(54001, "权限不存在");
    public static CodeMessage SAME_PERMISSION_NAME = new SystemCodeMessage(54002, "同一层级下存在相同名称的权限");
    public static CodeMessage HAS_CHILD_PERMISSION = new SystemCodeMessage(54050, "当前权限下面有子权限，无法删除");
    public static CodeMessage HAS_ROLE_IN_DEPARTMENT = new SystemCodeMessage(54051, "当前权限分配给了角色，无法删除");

}
