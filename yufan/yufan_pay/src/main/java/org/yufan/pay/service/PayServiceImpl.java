package org.yufan.pay.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.utils.JsonUtils;
import org.yufan.order.bean.Order;
import org.yufan.pay.bean.PayLog;
import org.yufan.pay.bean.Task;
import org.yufan.pay.client.OrderClient;
import org.yufan.pay.config.AlipayConfig;
import org.yufan.pay.config.RabbitMQConfig;
import org.yufan.pay.constant.PayConstant;
import org.yufan.pay.enums.PayStatusEnum;
import org.yufan.pay.enums.PayTypeEnum;
import org.yufan.pay.interceptor.UserThreadLocal;
import org.yufan.pay.repository.PayRepository;
import org.yufan.pay.repository.PayTaskRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zxd on 2019/4/6
 **/
@Service
@Slf4j
public class PayServiceImpl implements PayService{

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private OrderClient orderClient;

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PayTaskRepository payTaskRepository;

    @Override
    public String createPayToken(String orderId) throws CustomerException {
        Order order = orderClient.queryOrderByOrderId(orderId);
        if(ObjectUtils.isEmpty(order)){
             log.error("支付订单不存在!,orderId:{}",orderId);
            throw  new CustomerException(ResultEnum.PAY_ORDER_NOT_EXIST);
         }
         if(orderClient.queryOrderIsPay(orderId)){
             log.error("订单已支付!,orderId:{}",orderId);
             throw  new CustomerException(ResultEnum.PAY_ORDER_SUCCESS);
         }
         log.debug("创建预支付信息.........");
        PayLog payLog=new PayLog();
        payLog.setTotalFee(order.getTotalPay());
        payLog.setCreateTime(new Date());
        payLog.setOutTradeNo(orderId);
        payLog.setUserId(UserThreadLocal.getUser().getId());
        payLog.setTradeState(PayStatusEnum.WAIT.getCode());
        payRepository.save(payLog);
        log.debug("创建预支付信息为{}",JsonUtils.objectToJson(payLog));
        String payToken=UUID.randomUUID().toString();
        redisTemplate.boundValueOps(payToken).set(payLog.getOutTradeNo());
        redisTemplate.boundValueOps(payToken).expire(PayConstant.PAY_ORDER_EXPIRE,TimeUnit.MINUTES);
        return payToken;
    }

    private void buildPcPay(HttpServletResponse response,String orderId, BigDecimal totalAmount, String orderName, String itemDescribe) throws CustomerException {
        try {
            log.info("支付订单号:{}", orderId);
            log.info("支付金额:{}", totalAmount);
            log.info("支付订名称:{}", orderName);

            // 获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                    AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                    AlipayConfig.sign_type);
            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + orderId + "\"," + "\"total_amount\":\""
                    + totalAmount.toString() + "\"," + "\"subject\":\"" + orderName + "\"," + "\"body\":\""
                    + itemDescribe + "\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            String result=alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;character=utf-8");
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付请求失败{}", e);
            throw new CustomerException(ResultEnum.FAIL);
        }
    }


    private void buildMobilePay(HttpServletResponse response,String orderId, BigDecimal totalAmount, String orderName, String itemDescribe) throws CustomerException {
        try {
            log.info("支付订单号:{}", orderId);
            log.info("支付金额:{}", totalAmount);
            log.info("支付订名称:{}", orderName);
            //实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                    AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                    AlipayConfig.sign_type);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
           //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            // (model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(itemDescribe);
            model.setSubject(orderName);
            model.setOutTradeNo(orderId);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(totalAmount.toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(AlipayConfig.notify_url);
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse aliPayResponse = alipayClient.sdkExecute(request);
            //就是orderString 可以直接给客户端请求，无需再做处理。
            String result=aliPayResponse.getBody();
            log.info("返回客户端签名订单信息:{}，由客户端向支付宝发起支付请求!",request);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付请求失败{}", e);
            throw new CustomerException(ResultEnum.FAIL);
        }
    }

    @Override
    public boolean verifyResult() {

        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名

            return signVerified;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void pay(String payToken,Integer payType,HttpServletResponse response) throws CustomerException {
        if(StringUtils.isEmpty(payToken)){
            log.error("支付token不能为空");
            throw  new CustomerException(ResultEnum.PAY_TOKEN_NOT_EMPTY);
        }
        String payId= (String) redisTemplate.boundValueOps(payToken).get();
        if(StringUtils.isEmpty(payId)){
            log.error("支付请求超时!");
            throw  new CustomerException(ResultEnum.PAY_TOKEN_EXPIRE);
        }
        PayLog payLog= payRepository.getOne(payId);
        if(ObjectUtils.isEmpty(payLog)){
            log.error("未找到支付信息!");
            throw  new CustomerException(ResultEnum.PAY_INFORMATION_NOT_FOUND);
        }
        if(payType.equals(PayTypeEnum.PC.getCode())){
            log.info("电脑网页支付...");
            buildPcPay(response,payLog.getOutTradeNo(), BigDecimal.valueOf(payLog.getTotalFee()).divide(BigDecimal.valueOf(100)), "羽帆商城订单", null);
        }else if (payType.equals(PayTypeEnum.MOBILE.getCode())){
            log.info("手机app支付...");
            buildMobilePay(response,payLog.getOutTradeNo(), BigDecimal.valueOf(payLog.getTotalFee()).divide(BigDecimal.valueOf(100)), "羽帆商城订单", null);
        }else if(payType.equals(PayTypeEnum.MOBILE_WEB.getCode())){
            log.info("手机网页支付...");
            //TODO...
        }

    }


    @Override
    @Transactional
    public String asynCallBack(Map<String, String> params) throws CustomerException {
        if (!verifyResult()) {
            //验签失败
            log.error("验签失败!");
            throw  new CustomerException(ResultEnum.PAY_VERIFY_FAIL);
        }
        String outTradeNo = params.get("out_trade_no");
        //判断是否有支付订单信息
        PayLog payLog = payRepository.getOne(outTradeNo);
        if(payLog==null){
            log.error("支付信息不存在!");
            throw  new CustomerException(ResultEnum.PAY_INFORMATION_NOT_FOUND);
        }
        //判断订单状态是否已经支付
        if(payLog.getTradeState()==PayStatusEnum.SUCCESS.getCode()){
            log.info("订单已经支付,orderId:{}",outTradeNo);
            return "success";
        }
        //校验支付订单金额是否一致
        String totalAmount = params.get("total_amount");

        BigDecimal totalFee=BigDecimal.valueOf(payLog.getTotalFee()).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);;

        if(!totalFee.equals(new BigDecimal(totalAmount))){
            log.error("支付金额不正确,orderId:{},支付金额:{},订单金额:{}",outTradeNo,totalAmount,totalFee);
            throw  new CustomerException(ResultEnum.PAY_MONEY_FAIL);
        }
        // 支付宝交易号
        String tradeNo = params.get("trade_no");
        payLog.setPayTime(new Date());
        payLog.setTradeState(PayStatusEnum.SUCCESS.getCode());
        payLog.setTransactionId(tradeNo);

        //修改支付表订单状态
         payRepository.save(payLog);

         //消息中间件实现分布式事务事物方案,保存修改订单状态任务信息到任务表
        saveUpdateOrderStatusMsg(outTradeNo);

        //通知订单修改订单状态 分布式事物TCC方案
       //if(!orderClient.changeOrderStatePay(outTradeNo)){
         //      log.error("修改订单状态失败,回滚!");
           //    throw  new CustomerException(ResultEnum.FAIL);
       //}

       return "success";
    }

    private void saveUpdateOrderStatusMsg(String outTradeNo) {
        Task task=new Task();
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        //任务交换机
        task.setMqExchange(RabbitMQConfig.EX_ORDER);
        //任务路由key
        task.setMqRoutingkey(RabbitMQConfig.ORDER_UPDATE_KEY);
        //任务内容
        task.setRequestBody(outTradeNo);
        //乐观锁版本
        task.setVersion(0);
        //任务类型
        task.setTaskType(RabbitMQConfig.TASK_TYPE);
        payTaskRepository.save(task);
    }

}
