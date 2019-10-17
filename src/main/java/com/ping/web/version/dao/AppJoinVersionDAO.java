package com.ping.web.version.dao;

import com.ping.web.version.dto.AppJoinVersionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppJoinVersionDAO extends JpaRepository<AppJoinVersionDO, Long> {

    /**
     * 根据版本号获取关联实体
     * @param versionId 版本号
     * @return 关联实体
     */
    AppJoinVersionDO findByVersionId(Long versionId);
}
