package com.ping.web.version.dao;

import com.ping.web.version.dto.AppDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppDAO extends JpaRepository<AppDO, Long> {

    /**
     * 根据平台查询应用列表
     * @param platform 平台
     * @return
     */
    List<AppDO> findByPlatform(int platform);
    /**
     * 根据名称、平台查询应用列表
     * @param name 应用名称
     * @param platform 平台
     * @return
     */
    List<AppDO> findByNameAndPlatform(String name, int platform);

    /**
     * 根据包名、平台查询应用列表
     * @param packageName 包名（bundle_id)
     * @param platform 平台
     * @return
     */
    List<AppDO> findByPackageNameAndPlatform(String packageName, int platform);

    /**
     * 根据Token获取应用信息
     * @param token token信息
     * @return 应用信息
     */
    AppDO findByToken(String token);
}