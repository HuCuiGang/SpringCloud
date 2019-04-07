package org.yufan.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.ResultEnum;
import org.yufan.user.bean.User;
import org.yufan.user.repository.UserRepository;
import org.yufan.user.service.UserService;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {


    private static final String USER_TOKEN="user_token:";

    private static final String USER_LOGIN="login";

    public static   final String USER_LOGIN_SMS="user_login_sms:";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void register(User user) throws CustomerException {
        //1.参数校验
        checkUser(user);
        //2.判断信息是否已经使用
        judgeUser(user);
        //3.生成盐
        String salt=createSalt();
        //4.密码加密
        String md5Password=md5Password(user.getPassword(),salt);

        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        user.setSalt(salt);
        user.setPassword(md5Password);
        userRepository.save(user);
    }

    @Override
    public String login(String username, String password) throws CustomerException {

        if(StringUtils.isEmpty(username)){
            log.debug("用户名不能为空!");
            throw  new CustomerException(ResultEnum.USERNAME_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(password)){
            log.debug("密码不能为空!");
            throw  new CustomerException(ResultEnum.PASSWORD_NOT_EMPTY);
        }
        //获取用户信息
        User user = userRepository.findUserByUsername(username);
        if(user==null){
            log.info("用户不存在,{}",username);
            throw  new CustomerException(ResultEnum.LOGIN_FAIL);
        }

        checkIsLogin(user);

        //密码加密
        String md5Password=md5Password(password,user.getSalt());
        //比较密码
        if(!md5Password.equals(user.getPassword())){
            log.info("密码错误,用户名为{}",username);
            throw  new CustomerException(ResultEnum.FAIL);
        }


        String token = dealLogin(user);
        return token;
    }

    private void checkIsLogin(User user) throws CustomerException {
        String oldToken= (String) redisTemplate.boundHashOps(USER_LOGIN).get(user.getId());
        //判断是否重复登录
        if(!StringUtils.isEmpty(oldToken) ){


            //查询登录是否过期
            if(!ObjectUtils.isEmpty( redisTemplate.boundValueOps(USER_TOKEN+oldToken).get())){
                    log.info("用户已经登录!{}",user.getUsername());
                    throw  new CustomerException(ResultEnum.USER_ALREADY_LOGIN);
            }
            //登录过期
            log.info("登录过期,token:{}",oldToken);
        }
    }

    private String dealLogin(User user) {
        //处理登录
        String token=createToken();

        redisTemplate.boundValueOps(USER_TOKEN+token).set(user);
        redisTemplate.boundValueOps(USER_TOKEN+token).expire(2*60,TimeUnit.MINUTES);
        //记住登录状态
        redisTemplate.boundHashOps(USER_LOGIN).put(user.getId(),token);
        return token;
    }

    @Override
    public User queryUserByToken(String token) throws CustomerException {
        if(StringUtils.isEmpty(token) ){
            log.debug("token为空");
            throw  new CustomerException(ResultEnum.TOKEN_NOT_EMPTY);
        }
        User user= (User) redisTemplate.boundValueOps(USER_TOKEN+token).get();

        if(ObjectUtils.isEmpty(user)){
            log.info("token{},没有登录",token);
            throw  new CustomerException(ResultEnum.LOGIN_EXPIRY);
        }
        //刷新过期时间
        redisTemplate.boundValueOps(USER_TOKEN+token).expire(2*60,TimeUnit.MINUTES);
        return user;
    }


    @Override
    public String loginByPhone(String phone, String code) throws CustomerException {
        if(StringUtils.isEmpty(phone)){
            throw  new CustomerException(ResultEnum.PHONE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(code)){
            throw  new CustomerException(ResultEnum.CHECK_CODE_NOT_EMPTY);
        }
        //判断用户是否已经注册
        User user=userRepository.findUserByPhone(phone);
        if(user==null){
            throw  new CustomerException(ResultEnum.PHONE_NOT_REGISTER);
        }
        //检查是否重复登录
        checkIsLogin(user);

        String serverCode= (String) redisTemplate.boundValueOps(USER_LOGIN_SMS+phone).get();

        if(StringUtils.isEmpty(serverCode)){
            throw  new CustomerException(ResultEnum.CHECK_CODE_EXPIRY);
        }
        if(!code.equals(serverCode)){
            throw  new CustomerException(ResultEnum.CHECK_CODE_ERROR);
        }
        log.info("处理登录:{}，删除验证码{}",phone,serverCode);
        redisTemplate.delete(USER_LOGIN_SMS+phone);
        return  dealLogin(user);
    }



    /**
     * 生成token
     * @return
     */
    private String createToken() {
        return DigestUtils.md5DigestAsHex((new Date().getTime()+UUID.randomUUID().toString()).getBytes());
    }

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    private String md5Password(String password,String salt){
        return DigestUtils.md5DigestAsHex((password+salt).getBytes());
    }

    /**
     *生成盐
     * @return
     */
    private String createSalt() {
        return DigestUtils.md5DigestAsHex((new Date().getTime()+UUID.randomUUID().toString()).getBytes());
    }


    /**
     * 判断用户信息是否使用
     * @param user
     */
    private void judgeUser(User user) throws CustomerException {

        if(userRepository.findUserByUsername(user.getUsername())!=null){
            log.info("用户名已经使用:{}",user.getUsername());
            throw  new CustomerException(ResultEnum.USERNAME_EXIST);
        }
        if(userRepository.findUserByEmail(user.getEmail())!=null){
            log.info("邮箱已经使用:{}",user.getEmail());
            throw  new CustomerException(ResultEnum.EMAIL_EXIST);
        }
        if(userRepository.findUserByPhone(user.getPhone())!=null) {
            log.info("电话已经使用:{}", user.getPhone());
            throw new CustomerException(ResultEnum.PHONE_EXIST);
        }
    }

    /**
     * 参数校验
     * @param user
     * @throws CustomerException
     */
    private void checkUser(User user) throws CustomerException {
        if(user==null){
            log.error("用户信息为空!");
            throw  new CustomerException(ResultEnum.USER_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getUsername())){
            log.error("用户名为空!");
            throw  new CustomerException(ResultEnum.USERNAME_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            log.error("密码为空!");
            throw  new CustomerException(ResultEnum.PASSWORD_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getEmail())){
            log.error("邮箱为空!");
            throw  new CustomerException(ResultEnum.EMAIL_NOT_EMPTY);
        }
    }


    @Override
    public void sendLoginSms(String phone) throws CustomerException {
        if(StringUtils.isEmpty(phone)){
            throw  new CustomerException(ResultEnum.PHONE_NOT_EMPTY);
        }
        //判断用户是否已经注册
        if(userRepository.findUserByPhone(phone)==null){
            throw  new CustomerException(ResultEnum.PHONE_NOT_REGISTER);
        }
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append(new Random().nextInt(10));
        }
        String code=sb.toString();
        log.info("用户的验证码为:{}",code);
        redisTemplate.boundValueOps(USER_LOGIN_SMS+phone).set(code);
        redisTemplate.boundValueOps(USER_LOGIN_SMS+phone).expire(5,TimeUnit.MINUTES);

        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.rabbitTemplate.convertAndSend("yufan.sms.exchange", "sms.verify.code", msg);
            // 将code存入redis
        } catch (Exception e) {
            log.error("发送短信失败。phone：{}， code：{}", phone, code);
            throw  new CustomerException(ResultEnum.FAIL);
        }

    }



}
