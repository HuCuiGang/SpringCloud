package org.yufan.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yufan.sms.config.SmsProperties;
import org.yufan.sms.utils.SmsUtils;

import java.util.Map;

@Component
@EnableConfigurationProperties(SmsProperties.class)
@Slf4j
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties prop;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "yufan.sms.queue", durable = "true"),
            exchange = @Exchange(value = "yufan.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg, Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();
       try {

           if (msg == null || msg.size() <= 0) {
               // 放弃处理
               return;
           }


           String phone = msg.get("phone");
           String code = msg.get("code");

           if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
               // 放弃处理
               return;
           }
           // 发送消息
           SendSmsResponse resp = this.smsUtils.sendSms(phone, code,
                   prop.getSignName(),
                   prop.getVerifyCodeTemplate());

           if (resp.getCode().equals("OK")) {
               log.info("发送成功，手机号码为:{}", phone);
               channel.basicAck(tag,false);
           } else {
               log.error("发送失败，失败code为:{},message:{}", resp.getCode(), resp.getMessage());
               channel.basicNack(tag, false, true);
           }
       }catch (Exception e){
           channel.basicNack(tag, false, true);
           log.error("发送短信失败,系统出现未知异常:{}",e.getMessage());
           e.printStackTrace();
           throw new RuntimeException();
       }
    }
}