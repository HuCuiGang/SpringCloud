package org.yufan.pay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.lock.Lock;
import org.yufan.common.lock.ZookeeperDistrbuteLock;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;
import org.yufan.pay.service.PayService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



@Controller
@Slf4j
public class PayController {


    @Autowired
    private PayService payService;

    private Lock lock = new ZookeeperDistrbuteLock("/payLock");

    /**
     * 创建预支付信息
     * @param orderId
     * @return
     * @throws CustomerException
     */
    @PostMapping("/pay/createPayToken")
    @ResponseBody
    public Result cratePayToken(@RequestParam("orderId") String orderId) throws CustomerException {
        log.info("预支付订单号为:{}",orderId);
        String token = payService.createPayToken(orderId);
        log.info("创建支付token为:{}",token);
        return ResultUtils.buildSuccess(token);
    }

    /**
     * 发起支付请求
     * @param payToken
     * @param response
     * @throws CustomerException
     * @throws IOException
     */
    @RequestMapping("/pay/create")
    public void pay(@RequestParam("payToken")  String payToken, Integer payType, HttpServletResponse response) throws CustomerException, IOException {
        payService.pay(payToken,payType,response);
    }

    //同步通知
    @RequestMapping("/callback/returnUrl")
    @ResponseBody
    public Result synCallBack(@RequestParam Map<String, String> params) {
        log.info("#####支付宝同步通知synCallBack#####开始,params:{}", params);
        try {
            if (!payService.verifyResult()) {
                return ResultUtils.buildFail(ResultEnum.FAIL);
            }
            // 商户订单号
            String outTradeNo = params.get("out_trade_no");
            // 支付宝交易号
            String tradeNo = params.get("trade_no");
            // 付款金额
            String totalAmount = params.get("total_amount");
            Map map=new HashMap();
            map.put("outTradeNo",outTradeNo);
            map.put("tradeNo",tradeNo);
            map.put("totalAmount",totalAmount);
            return ResultUtils.buildSuccess(map);
        } catch (Exception e) {
            log.error("####支付宝同步通知出现异常,ERROR:", e);
            return ResultUtils.buildFail(ResultEnum.FAIL);
        } finally {
            log.info("#####支付宝同步通知synCallBack#####结束,params:{}", params);
        }

    }


    //异步通知
    @PostMapping("/callback/notify")
    @ResponseBody
    public String payNotify(@RequestParam Map<String, String> params){
        log.info("#####支付宝异步通知synCallBack#####开始,params:{}", params);
        try {
            lock.getLock();
            return payService.asynCallBack(params);
        }catch (CustomerException e) {
            log.error("支付通知失败!原因:{}",e.getMessage());
            return "fail";
         } catch (Exception e){
            log.error("支付通知失败!原因:{}",e.getMessage());
            return "fail";
        }finally {
            log.info("#####支付宝异步通知synCallBack#####结束,params:{}", params);
            lock.unLock();
        }
    }


}
