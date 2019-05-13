package org.yufan.cart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yufan.cart.UserThreadLocal;
import org.yufan.cart.bean.Cart;
import org.yufan.cart.service.CartService;
import org.yufan.common.result.Result;
import org.yufan.common.result.ResultEnum;
import org.yufan.common.result.ResultUtils;

import java.util.List;

@RestController
@Slf4j
@Api("购物车接口")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    @ApiOperation(value = "添加商品购物车", notes = "加入购物车")
    @ApiImplicitParam(name = "token", required = true, value = "token",paramType = "header")
    public Result addCart(Cart cart){
        cartService.addCart(cart);
        return ResultUtils.buildSuccess();
    }


    @ApiOperation(value = "查询用户购物车",
            notes = "查询用户购物车")
    @ApiImplicitParam(name = "token", required = true, value = "token",paramType = "header")
    @GetMapping("/cart")
    public Result queryCart(){
        List<Cart> cartList = cartService.queryCartByUserId(UserThreadLocal.getUser().getId());
        return ResultUtils.buildSuccess(ResultEnum.OK,cartList);
    }

    @ApiOperation(value = "修改购物车数量",
            notes = "修改购物车数量")
    @PutMapping("/cart/{itemId}/{num}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", required = true, value = "token",paramType = "header"),
            @ApiImplicitParam(name = "itemId", required = true, value = "商品id",paramType = "path"),
            @ApiImplicitParam(name = "num", required = true, value = "数量",paramType = "path")
    })
    public Result updateCartNum(@PathVariable("itemId")Long itemId,@PathVariable("num")Integer num){
        cartService.updateCartNum(itemId,UserThreadLocal.getUser().getId(),num);
        return ResultUtils.buildSuccess();
    }

    @ApiOperation(value = "删除购物项",
            notes = "删除购物项")
    @DeleteMapping("/cart/{itemId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", required = true, value = "token",paramType = "header"),
            @ApiImplicitParam(name = "itemId", required = true, value = "商品id",paramType = "path"),
    })
    public Result deleteCartItem(@PathVariable("itemId") Long itemId){
          cartService.deleteCart(itemId,UserThreadLocal.getUser().getId());
        return ResultUtils.buildSuccess();
    }

}
