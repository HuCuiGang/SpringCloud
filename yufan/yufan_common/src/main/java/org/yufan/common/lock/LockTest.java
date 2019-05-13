package org.yufan.common.lock;

import java.util.UUID;

/**
 * 分布式锁测试
 * Created by zxd on 2019/1/18
 **/
public class LockTest implements Runnable {

    private Lock lock = new ZookeeperDistrbuteLock("/orderlock");
    public void run() {
        getNumber();
    }
    public void getNumber() {
        try {
            //获取锁
            lock.getLock();
            String number = UUID.randomUUID().toString();
            System.out.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }
    public static void main(String[] args) {
        System.out.println("####生成唯一订单号###");
        for (int i = 0; i < 100; i++) {
            new Thread( new LockTest()).start();
        }
    }
}