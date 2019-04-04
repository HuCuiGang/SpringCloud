package org.yufan.common.result;

import lombok.Getter;

@Getter
public enum ResultEnum {

    OK(20000,"请求成功"),
    USERNAME_NOT_EMPTY(20001,"用户名不能为空"),
    PASSWORD_NOT_EMPTY(20002,"密码不能为空"),
    LOGIN_FAIL(20003,"用户名密码错误"),
    USER_ALREADY_LOGIN(20004,"用户已经登录"),
    TOKEN_NOT_EMPTY(20005,"token为空"),
    LOGIN_EXPIRY(20006,"登录失效"),
    PHONE_NOT_EMPTY(20007,"手机号码不能为空"),
    CHECK_CODE_NOT_EMPTY(20008,"验证码不能为空"),
    PHONE_NOT_REGISTER(20009,"手机号码未注册"),
    CHECK_CODE_EXPIRY(200010,"验证码失效"),
    CHECK_CODE_ERROR(200011,"验证码输入错误"),
    USERNAME_EXIST(200012,"用户名已存在"),
    PHONE_EXIST(200013,"手机号码已存在"),
    EMAIL_EXIST(200014,"邮箱已存在"),
    USER_NOT_EMPTY(200015,"用户名信息为空"),
    EMAIL_NOT_EMPTY(200016,"邮箱不能为空"),
    PHONE_ERROR(200017,"手机号码格式不符合"),


    FAIL(50000,"请求失败,系统开小差了!");

    ResultEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private int code ;
    private String msg;
}
