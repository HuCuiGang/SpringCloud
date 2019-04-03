package org.yufan.userinfo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;
import org.yufan.user.bean.User;
import org.yufan.user.service.UserService;
import org.yufan.userinfo.bean.UserInfo;
import org.yufan.userinfo.service.UserInfoService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tyd
 * @create 2019-04-03 {TIME}
 */
@RestController
@RequestMapping("/user_info")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/saveOrUpdate/{token}")
    public Result saveOrUpdate(@PathVariable String token, @Valid UserInfo userInfo, BindingResult bindingResult) throws CustomerException {

        if(bindingResult.hasErrors()){
            log.info("校验不通过!");
            List<ObjectError> errorList = bindingResult.getAllErrors();
            StringBuilder sb=new StringBuilder();
            for (ObjectError error : errorList) {
                String msg=error.getDefaultMessage();
                log.info("失败原因:{}",msg);
                sb.append(msg+",");
            }
            return new Result(false,30000,sb.toString().substring(0,sb.toString().length()-1));
        }

        if (StringUtils.isEmpty(token)){
            log.info("token为空！");
            throw new CustomerException(ResultEnum.TOKEN_NOT_EMPTY);
        }

        User user=userService.queryUserByToken(token);
        if (user==null){
            log.info("登陆失效！");
            throw new CustomerException(ResultEnum.LOGIN_EXPIRY);
        }
        UserInfo orderUserInfo=userInfoService.findOne(user.getId());
        if (orderUserInfo==null){
            log.info("还没有用户信息");
            userInfo.setUserId(user.getId());
            UserInfo result= userInfoService.save(userInfo);
            if (result==null){
                log.info("用户信息保存失败！");
                return ResultUtils.buildFail(ResultEnum.FAIL);
            }

        }

        userInfo.setId(orderUserInfo.getId());
        UserInfo result= userInfoService.save(userInfo);
        if (result==null){
            log.info("用户信息保存失败！");
            return ResultUtils.buildFail(ResultEnum.FAIL);
        }

        return ResultUtils.buildSuccess();

    }

}
