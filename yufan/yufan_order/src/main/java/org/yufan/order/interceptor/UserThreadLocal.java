package org.yufan.order.interceptor;

import org.yufan.user.bean.User;

public class UserThreadLocal {

    private static ThreadLocal<User> tl=new ThreadLocal<User>();

    public static void setUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
