package org.yufan.common.lock;

/**
 * Created by zxd on 2019/1/18
 **/
public interface Lock {
        //获取到锁的资源
        public void getLock();
        // 释放锁
        public void unLock();
}
