package com.sjh.enums;

/**
 * @author 宋佳豪
 * @version 1.0
 */
public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONE_NUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必须填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    NO_EXIST(506, "用户不存在"),
    NEED_PARAMS(507, "缺少参数或参数错误"),
    CONTENT_NO_NULL(508, "评论内容不能为空"),
    FILE_TYPE_ERROR(509, "文件类型错误,请上传png类型"),
    FILE_CANNOT_NULL(510, "文件不能为空"),
    USERNAME_NO_EXIST(511, "用户名不存在"),
    PASSWORD_NO_EXIST(512, "密码不存在"),
    EMAIL_NO_EXIST(513, "邮箱不存在"),
    NICKNAME_NO_EXIST(514, "昵称不存在"),
    NICKNAME_EXIST(515, "昵称已存在"),
    TAG_NAME_EXIST(516,"标签名已存在");

    private final int code;
    private final String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
