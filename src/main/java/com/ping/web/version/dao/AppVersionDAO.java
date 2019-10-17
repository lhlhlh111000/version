package com.ping.web.version.dao;

import com.ping.web.version.dto.AppVersionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AppVersionDAO extends JpaRepository<AppVersionDO, Long> {


    /**
     * 根据app id获取版本列表
     * @param appId app id
     * @return 版本列表
     */
    List<AppVersionDO> findByAppIdOrderByCreateTimeDesc(Long appId);

    List<AppVersionDO> findByAppIdAndIdNotLike(Long appId, Long versionId);

    /**
     * 根据app id获取版本列表
     * @param appId app id
     * @param isEnable 是否可用
     * @return 版本列表
     */
    List<AppVersionDO> findByAppIdAndEnableOrderByBuildDesc(Long appId, boolean isEnable);

    /**
     * 切换指定版本是否启用
     * @param versionId 版本id
     * @param isEnable 是否可用
     */
    @Transactional
    @Query(value = "update app_version v set v.enable = ?2 where v.id=?1", nativeQuery = true)
    @Modifying
    void switchVersion(Long versionId, boolean isEnable);
}