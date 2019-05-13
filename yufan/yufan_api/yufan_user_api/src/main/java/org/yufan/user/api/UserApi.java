package org.yufan.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yufan.user.bean.User;

@RequestMapping("/api/user")
public interface UserApi {

    @GetMapping(value = "/query")
    public User queryUserByToken(@RequestParam("token") String token);

}
