package org.yufan.userinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.userinfo.bean.UserInfo;

/**
 * @author tyd
 * @create 2019-04-03 {TIME}
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

}
