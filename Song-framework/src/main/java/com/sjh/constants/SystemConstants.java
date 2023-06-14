package com.sjh.constants;

/**
 * 字面值 - 常量类
 *
 * @author 宋佳豪
 * @version 1.0
 */
public class SystemConstants {

    /**
     * 文章状态 - 草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章状态 - 正常发布
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 状态 - 正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链状态 - 审核通过
     */
    public static final String LINK_STATUS_CHECKED_PASS = "0";

    /**
     * 友链状态 - 审核未通过
     */
    public static final String LINK_STATUS_CHECKED_NOT_PASS = "1";

    /**
     * 友联状态 - 未审核
     */
    public static final String LINK_STATUS_UNCHECKED = "2";

    /**
     * 用户类型 - 管理员
     */
    public static final String USER_TYPE_ADMIN = "1";

    /**
     * 用户类型 - 普通用户
     */
    public static final String USER_TYPE_NORMAL_USER = "0";

    /**
     * 用户状态 - 正常
     */
    public static final String USER_STATUS_NORMAL = "0";

    /**
     * 用户状态 - 禁用
     */
    public static final String USER_STATUS_DISABLED = "1";

    /**
     * 性别 - 男
     */
    public static final String GENDER_MAN = "0";

    /**
     * 性别 - 女
     */
    public static final String GENDER_WOMAN = "1";

    /**
     * 性别 - 未知
     */
    public static final String GENDER_UNKNOWN = "2";

    /**
     * 根评论
     */
    public static final int IS_COMMENT_ROOT = -1;

    /**
     * 评论类型 - 文章评论
     */
    public static final String COMMENT_ARTICLE = "0";

    /**
     * 评论类型 - 友联评论
     */
    public static final String COMMENT_LINK = "1";

    /**
     * Redis key值 - 文章浏览量的key
     */
    public static final String REDIS_KEY_ARTICLE_VIEW_COUNT = "article:viewCount";

    /**
     * 角色 - 管理员
     */
    public static final String ROLE_KEY_ADMIN = "admin";

    /**
     * 权限类型 - 目录
     */
    public static final String PERMISSION_DIR = "M";

    /**
     * 权限类型 - 菜单
     */
    public static final String PERMISSION_MENU = "C";

    /**
     * 权限类型 - 按钮
     */
    public static final String PERMISSION_BUTTON = "F";

    /**
     * 后台系统用户登录Key
     */
    public static final String ADMIN_LOGIN_KEY = "adminLogin:";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

}

