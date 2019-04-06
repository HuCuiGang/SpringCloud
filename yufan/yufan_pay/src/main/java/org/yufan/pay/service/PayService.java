package org.yufan.pay.service;

import org.yufan.common.exception.CustomerException;

import java.util.Map;

/**
 * Created by zxd on 2019/1/14
 **/
public interface PayService {


    /**
     * 创建预支付信息
     * @param orderId
     * @return
     */
    public String createPayToken(String orderId) throws CustomerException;


    /**
     * 支付结果确认
     * @param request
     * @return true成功  false失败
     */
    public boolean verifyResult();

    /**
     * 发起支付请求
     * @param payToken
     * @return
     */
    public void pay(String payToken,Integer payType) throws CustomerException;


    /**
     * 支付异步通知
     * @param params
     * @return
     */
    public String asynCallBack(Map<String, String> params) throws CustomerException;
}
