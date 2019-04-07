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
<<<<<<< HEAD
    ITEM_NOT_EMPTY(200018,"商品为空"),
    ITEM_EXIST(200019,"商品已经存在"),
    ORDER_CREATE_VALID(200020,"创建订单参数不正确"),
    ITEM_SEARCH_ID_EMPTY(200020,"不合法的商品id"),
    FAIL(50000,"请求失败,系统开小差了"),
    PRODUCT_NOT_EXIST(200021,"下单商品不存在"),
    PAY_ORDER_NOT_EXIST(200022,"支付订单不存在"),
    PAY_ORDER_SUCCESS(200023,"订单已支付"),
    PAY_TOKEN_NOT_EMPTY(200024,"支付token不能为空"),
    PAY_TOKEN_EXPIRE(200025,"支付请求超时"),
    PAY_INFORMATION_NOT_FOUND(200026,"支付信息未找到"),
    SEARCH_ITEM_TITLE_EMPTY(200027,"商品名称为空"),
    PAY_VERIFY_FAIL(200028,"验签失败"),
    PAY_MONEY_FAIL(200029,"支付金额不正确"),
    ITEM_STOCK_ENOUGH(200030,"商品库存不足"),
    SEARCH_ITEM_HTTP_ADDRESS(200040,"同步商品的接口地址为空"),
    SEARCH_CATEGORY_SEARCH_EMPTY(200041,"目录为空"),
    SEARCH_ITEM_HTTP_DATA(200042,"同步商品的数据为空");
=======


    FAIL(50000,"请求失败,系统开小差了!");
>>>>>>> 23a132ed832f06d1bac6d76b2baf9d24c3cdd3d2

    ResultEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private int code ;
    private String msg;
}
