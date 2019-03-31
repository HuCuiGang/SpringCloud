package org.yufan.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;
import org.yufan.user.bean.User;
import org.yufan.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private Logger LOGGER=LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/register")
    public Result register(@Valid User user, BindingResult bindingResult) throws CustomerException {
        if(bindingResult.hasErrors()){
          LOGGER.info("校验不通过!");
            List<ObjectError> errorList = bindingResult.getAllErrors();
            StringBuilder sb=new StringBuilder();
            for (ObjectError error : errorList) {
                String msg=error.getDefaultMessage();
                LOGGER.info("失败原因:{}",msg);
                sb.append(msg+",");
            }
            return new Result(false,30000,sb.toString().substring(0,sb.toString().length()-1));
        }
        userService.register(user);
        return ResultUtils.buildSuccess();
    }


    @PostMapping(value = "/login")
    public Result  login(String username,String password) throws CustomerException {
        if(StringUtils.isEmpty(username)){
            LOGGER.debug("用户名不能为空!");
            return  ResultUtils.buildFail(ResultEnum.USERNAME_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.debug("密码不能为空!");
            return  ResultUtils.buildFail(ResultEnum.PASSWORD_NOT_EMPTY);
        }
        String token=userService.login(username,password);
        return ResultUtils.buildSuccess(ResultEnum.OK,token);
    }

    @GetMapping(value = "/query/{token}")
    public Result   queryUserByToken(@PathVariable String token) throws CustomerException {
        User user=userService.queryUserByToken(token);
        return ResultUtils.buildSuccess(ResultEnum.OK,user);
    }

    @GetMapping(value = "/login/{phone}/{code}")
    public Result  loginByPhone(@PathVariable("phone") String phone,@PathVariable("code") String code) throws CustomerException {
        String token=userService.loginByPhone(phone,code);
        return ResultUtils.buildSuccess(ResultEnum.OK,token);
    }


    @RequestMapping("/login/{phone}")
    public Result sendLoginSms(@PathVariable String phone) throws CustomerException {
        if(!phone.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")){
            return ResultUtils.buildFail(ResultEnum.PHONE_ERROR);
        }
        userService.sendLoginSms(phone);
        return ResultUtils.buildSuccess();
    }





}
