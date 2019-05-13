package org.yufan.user.service;


import org.yufan.common.exception.CustomerException;
import org.yufan.user.bean.User;

public interface UserService {

    /**
     *
     * @param user
     * @throws CustomerException
     */
    public void register(User user)throws CustomerException;

    /**
     * @param username
     * @param password
     * @return  token
     */
    public String  login(String username, String password) throws CustomerException;

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     * @throws CustomerException
     */
    public User queryUserByToken(String token) throws CustomerException;


    public String loginByPhone(String phone, String code) throws CustomerException;

    public void sendLoginSms(String phone) throws CustomerException;
}
