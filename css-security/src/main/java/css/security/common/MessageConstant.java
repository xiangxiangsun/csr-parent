package css.security.common;

/**
 *消息常量
 */
public class MessageConstant {
    public static final String GET_USERNAME_SUCCESS = "获取当前登录用户名成功";
    public static final String GET_MENU_SUCCESS = "获取当前登录用户菜单成功";

    public static final String GET_DEPT_SUCCESS = "获取当前登录用户部门成功";
    public static final String UPDATE_DEPTNAME_NOTUNIQUE_ERROR = "失败，当前部门名称已经存在";
    public static final String UPDATE_PARENTID_NOTSELF_ERROR = "失败，上级部门不能是自己";
    public static final String UPDATE_DEPT_SUCCESS = "更新当前部门信息成功";
    public static final String ADD_DEPT_SUCCESS = "添加部门信息成功";
    public static final String DELETE_DEPT_SUCCESS = "删除部门信息成功";
}
