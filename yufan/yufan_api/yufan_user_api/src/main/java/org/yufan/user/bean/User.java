package org.yufan.user.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

     @Id
     @GeneratedValue
     private Long id;

     @NotEmpty(message = "用户名不能为空!")
     @Length(min = 5,message ="用户名必须大于5位" )
     private String username;

     @NotEmpty(message = "密码不能为空!")
     @Length(min = 6,message ="用户名必须大于5位" )
     @JsonIgnore
     private String password;

     @NotEmpty(message = "电话号码能为空!")
     @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",message = "电话号码格式不正确!")
     private String phone;

     @NotEmpty(message = "邮箱不能为空!")
     @Email(message = "邮箱格式不合法")
     private String email;

     @JsonIgnore
     private String salt;
     private Date created;
     private Date updated;


}
