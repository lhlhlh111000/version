package com.ping.web.version.dao;

import com.ping.web.version.dto.RNVersionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RNVersionDAO extends JpaRepository<RNVersionDO, Long> {

    /**
     * 根据版本id查询RN版本列表
     * @param versionId 版本Id
     * @return RN版本列表
     */
    List<RNVersionDO> findByVersionIdOrderByVersionDesc(Long versionId);

}
