package org.yufan.user.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yufan.common.exception.CustomerException;
import org.yufan.user.bean.User;
import org.yufan.user.service.UserService;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserApiService implements UserApi {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/query")
    @Override
    public User queryUserByToken(String token) {
        log.info("查询信息的token为:{}",token);
        try {
            log.info("查询成功!");
            return userService.queryUserByToken(token);
        } catch (CustomerException e) {
            e.printStackTrace();
            log.error("查询失败,{}",e.getMessage());
            return null;
        }
    }
}
