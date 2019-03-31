package org.yufan.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yufan.cart.bean.Cart;

import java.util.*;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String CART="cart";

    @Override
    public void addCart(Cart cart) {

        //1.获取原来的购物车
        List<Cart> cartList = queryCartByUserId(cart.getUserId());

        cart.setUpdated(new Date());
        //2.判断是否有购物车集合 没有购物车就创建购物车集合
        if(CollectionUtils.isEmpty(cartList)){
            cartList=new ArrayList<Cart>();
            cartList.add(cart);
            cart.setCreated(new Date());
            redisTemplate.boundHashOps(CART).put(cart.getUserId(),cartList);
            return;
        }

        //先判断有没有，没有就创建，有就增加数量

        //cart  userId 1 itemId 1  num 1
        //cart   userId 1  itemId 1 num 2

        ListIterator<Cart> it = cartList.listIterator();

        while(it.hasNext()){

            Cart oldCart=it.next();

            if (oldCart.getItemId().equals(cart.getItemId()) && oldCart.getUserId().equals(cart.getUserId())) {
                //以前已经加入过了   //获取以前的数量 加入数量
                cart.setNum(cart.getNum() + oldCart.getNum());
                cart.setCreated(oldCart.getCreated());
                it.remove();
                break;
            }
        }


        if(cart.getCreated()==null){
            cart.setCreated(cart.getUpdated());
        }

        //添加购物车到集合中
        cartList.add(cart);
        //覆盖以前的数据
        redisTemplate.boundHashOps(CART).put(cart.getUserId(),cartList);
    }

    @Override
    public void updateCartNum(Long itemId, Long userId, Integer num) {

        //1.获取原来的购物车
        List<Cart> cartList = queryCartByUserId(userId);
        if(CollectionUtils.isEmpty(cartList)){
            return;
        }

        Cart cart=null;
        //2.获取购物项
        ListIterator<Cart> it = cartList.listIterator();
        while(it.hasNext()){
            Cart oldCart=it.next();
            if (oldCart.getItemId().equals(itemId) && oldCart.getUserId().equals(userId)) {
                cart=oldCart;
                it.remove();//移除原来的购物项
                break;
            }
        }
        if(cart==null){
            return;
        }
        //覆盖原来的数据
        cart.setNum(num);
        cartList.add(cart);
        redisTemplate.boundHashOps(CART).put(cart.getUserId(),cartList);
    }

    @Override
    public void deleteCart(Long itemId, Long userId) {
        //1.获取原来的购物车
        List<Cart> cartList = queryCartByUserId(userId);

        if(CollectionUtils.isEmpty(cartList)){
            return;
        }

        ListIterator<Cart> it = cartList.listIterator();
        while(it.hasNext()){
            Cart oldCart=it.next();
            if (oldCart.getItemId().equals(itemId) && oldCart.getUserId().equals(userId)) {
                it.remove();//删除购物项
                break;
            }
        }
        redisTemplate.boundHashOps(CART).put(userId,cartList);
    }

    @Override
    public List<Cart> queryCartByUserId(Long userId) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps(CART).get(userId);
        return cartList;
    }
}
