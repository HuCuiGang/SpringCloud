package org.yufan.userinfo.bean;


import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author tyd
 * @create 2019-04-03 {TIME}
 */
@Entity
@Table(name = "user_info")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId=new Long(0);

    /** 用户名 */
    @NotEmpty(message = "用户名不能为空!")
    @Length(min = 5,message ="用户名必须大于5位" )
    private String username;

    /** 手机号 */
    @NotEmpty(message = "电话号码能为空!")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",message = "电话号码格式不正确!")
    private String phone;

    /** 性别 */
    private Integer sex;

    /** 邮箱 */
    @NotEmpty(message = "邮箱不能为空!")
    @Email(message = "邮箱格式不合法")
    private String email;

    /** 身份证号 */
    private String idcard;

    /** 婚姻状况 */
    private String maritalStatus;

    /** 所在行业 */
    private String industry;

    /** 薪资 */
    private double salary=0;

    /** 头像地址 */
    private String picUrl;



}
