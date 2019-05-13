package org.yufan.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zxd on 2019/1/18
 **/
@Slf4j
public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {
    private CountDownLatch countDownLatch = null;

    /**
     * 创建父节点
     *
     * @param path
     */
    public ZookeeperDistrbuteLock(String path) {
        super(path);
    }


    /**
     *  获取锁资源实现
     * @return
     */
    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(path);
            log.debug("创建临时节点成功,{}",path);
            return true;
        } catch (Exception e) {
            log.debug("创建临时节点失败，锁被占用!{}",path);
            return false;
        }

    }

    /**
     * 等待
     */
    @Override
    void waitLock() {
        IZkDataListener izkDataListener = new IZkDataListener() {

            public void handleDataDeleted(String path) throws Exception {
                if (countDownLatch != null) {
                    log.debug("临时节点被删除，唤醒被等到线程!");
                    countDownLatch.countDown();
                }
            }
            public void handleDataChange(String path, Object data) throws Exception {

            }
        };

        log.debug("注册事件");
        zkClient.subscribeDataChanges(path, izkDataListener);
        if (zkClient.exists(path)) {

            countDownLatch = new CountDownLatch(1);
            try {
                log.debug("锁已被占用，等待!!!!!");
                countDownLatch.await();
                log.debug("释放锁，继续执行...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug("删除事件...");
        zkClient.unsubscribeDataChanges(path, izkDataListener);
    }

}
