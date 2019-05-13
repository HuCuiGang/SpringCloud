package org.yufan.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;


@Slf4j
public abstract class ZookeeperAbstractLock implements Lock {
    // zk连接地址
    private static final String CONNECTSTRING = "127.0.0.1:2181";
    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);

    private static final String PARENT_PATH="/distrbuteLock";
    protected  String path;

    /**
     * 创建父节点
     * @param path
     */
    public ZookeeperAbstractLock(String path){
                if(!zkClient.exists(PARENT_PATH)){
                    zkClient.createPersistent(PARENT_PATH);
                }
                this.path=PARENT_PATH+path;
    }

    /**
     * 获取到锁的资源
     */
    @Override
    public void getLock() {
        if (tryLock()) {
            log.debug("##获取lock锁的资源####");
        } else {
            // 等待
            waitLock();
            // 重新获取锁资源
            getLock();
        }

    }

    /**
     * 释放锁
     */
    @Override
    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
            log.debug("释放锁资源......");
        }
    }


    /**
     *  获取锁资源
     * @return
     */
    abstract boolean tryLock();


    /**
     * 等待
     */
    abstract void waitLock();



}