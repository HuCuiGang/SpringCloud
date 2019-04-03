package org.yufan.user_info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.user_info.bean.UserInfo;

/**
 * @author tyd
 * @create 2019-04-03 {TIME}
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

}
