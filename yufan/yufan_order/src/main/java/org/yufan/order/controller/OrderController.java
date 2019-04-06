package org.yufan.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yufan.common.exception.CustomerException;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;
import org.yufan.order.bean.OrderDTO;
import org.yufan.order.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    //1.下单
    @PostMapping("/create")
    public Result create(@Valid OrderDTO orderDTO, BindingResult bindingResult) throws CustomerException {
        if (bindingResult.hasErrors()) {
            List<String> errors =
                    bindingResult.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage).
                            collect(Collectors.toList());
            log.error("创建订单参数不正确, orderDTO={}", orderDTO);
            return new Result(false,200020,errors.toString(),null);
        }
        String orderId=orderService.createOrder(orderDTO);
        return ResultUtils.buildSuccess(ResultEnum.OK,orderId);
    }



}
