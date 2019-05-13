package org.yufan.userinfo.service;

import org.yufan.common.exception.CustomerException;
import org.yufan.userinfo.bean.UserInfo;

/**
 * @author tyj
 * @create 2019-04-03 19:52
 */
public interface UserInfoService {

    public UserInfo save(UserInfo userInfo) throws CustomerException;

    public UserInfo findOne(Long userId);

}
