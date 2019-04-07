package org.yufan.userinfo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.ResultEnum;
import org.yufan.userinfo.bean.UserInfo;
import org.yufan.userinfo.repository.UserInfoRepository;
import org.yufan.userinfo.service.UserInfoService;



/**
 * @author tyj
 * @create 2019-04-03 19:53
 */
@Service
@Transactional
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) throws CustomerException {
       //判断信息是否已实用
       judgeUserInfo(userInfo);
       return userInfoRepository.save(userInfo);

    }

    @Override
    public UserInfo findOne(Long id) {

        UserInfo userInfo = userInfoRepository.findUserInfoByUserId(id);
        return userInfo;
    }
    /**
     * 判断用户详细信息是否使用
     * @param userInfo
     */
    private void judgeUserInfo(UserInfo userInfo) throws CustomerException {

        if(userInfoRepository.findUserInfoByUsername(userInfo.getUsername())!=null){
            log.info("用户名已经使用:{}",userInfo.getUsername());
            throw  new CustomerException(ResultEnum.USERNAME_EXIST);
        }
        if(userInfoRepository.findUserInfoByEmail(userInfo.getEmail())!=null){
            log.info("邮箱已经使用:{}",userInfo.getEmail());
            throw  new CustomerException(ResultEnum.EMAIL_EXIST);
        }
        if(userInfoRepository.findUserInfoByPhone(userInfo.getPhone())!=null) {
            log.info("电话已经使用:{}", userInfo.getPhone());
            throw new CustomerException(ResultEnum.PHONE_EXIST);
        }
    }
}
