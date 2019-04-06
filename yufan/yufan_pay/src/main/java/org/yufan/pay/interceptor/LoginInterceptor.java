package org.yufan.pay.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;
import org.yufan.common.utils.JsonUtils;
import org.yufan.common.utils.ResponseUtils;
import org.yufan.pay.client.UserClient;
import org.yufan.user.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private UserClient userClient;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token=request.getParameter("token");
            if(StringUtils.isEmpty(token)){
                log.error("token不能为空 ");
                ResponseUtils.responseJson(response,JsonUtils.objectToJson(ResultUtils.buildFail(ResultEnum.TOKEN_NOT_EMPTY)));
                return false;
            }
        }
        User user=userClient.queryUserByToken(token);
        if(ObjectUtils.isEmpty(user)){
            log.error("登录失效, token:{}",token);
            ResponseUtils.responseJson(response,JsonUtils.objectToJson(ResultUtils.buildFail(ResultEnum.LOGIN_EXPIRY)));
            return false;
        }
        UserThreadLocal.setUser(user);
        log.info("登录信息正常,放行, 用户名为:{}",user.getUsername());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeUser();
    }
}
