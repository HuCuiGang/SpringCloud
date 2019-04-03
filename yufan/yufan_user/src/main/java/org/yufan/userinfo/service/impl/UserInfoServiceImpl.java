package org.yufan.userinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yufan.userinfo.bean.UserInfo;
import org.yufan.userinfo.repository.UserInfoRepository;
import org.yufan.userinfo.service.UserInfoService;



/**
 * @author tyj
 * @create 2019-04-03 19:53
 */
@Service
@Transactional
@javax.transaction.Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
       return userInfoRepository.save(userInfo);

    }

    @Override
    public UserInfo findOne(Long id) {

        UserInfo userInfo = userInfoRepository.getOne(id);
        return userInfo;
    }

}
