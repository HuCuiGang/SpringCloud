package org.yufan.pay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yufan.pay.bean.Task;

import java.util.Date;

/**
 * Created by zxd on 2019/4/6
 **/
public interface PayTaskRepository extends JpaRepository<Task,String> {

    /**
     * 查询某个时间之间的前n条任务
     * @param pageable
     * @param updateTime
     * @return
     */
    Page<Task> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * 更新updateTime
     * @param id
     * @param updateTime
     * @return
     */
    @Modifying
    @Query("update Task t set t.updateTime = :updateTime where t.id = :id")
    public int updateTaskTime(@Param(value = "id") String id, @Param(value = "updateTime") Date updateTime);

    @Modifying
    @Query("update Task t set t.version = :version+1 where t.id = :id and t.version = :version")
    public int updateTaskVersion(@Param(value = "id") String id, @Param(value = "version") int version);
}
