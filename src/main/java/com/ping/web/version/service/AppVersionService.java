package com.ping.web.version.service;

import com.ping.web.version.dao.AppJoinVersionDAO;
import com.ping.web.version.dao.AppVersionDAO;
import com.ping.web.version.dto.AppJoinVersionDO;
import com.ping.web.version.dto.AppVersionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppVersionService {

    @Autowired
    private AppVersionDAO appVersionDAO;

    @Autowired
    private AppJoinVersionDAO appJoinVersionDAO;


    /**
     * 查询所有App版本信息
     * @return app版本列表
     */
    public List<AppVersionDO> queryAllAppVersion() {
        return appVersionDAO.findAll();
    }

    /**
     * 获取版本信息
     * @param versionId 版本id
     * @return 版本信息
     */
    public AppVersionDO queryByVersionId(Long versionId) {
        return appVersionDAO.findById(versionId).get();
    }

    /**
     * 根据app id查询版本
     * @param appId app id
     * @return 版本列表
     */
    public List<AppVersionDO> queryByAppId(Long appId) {
        return appVersionDAO.findByAppIdOrderByCreateTimeDesc(appId);
    }

    /**
     * 根据app id查询版本，排除指定版本
     * @param appId app id
     * @param versionId 排除版本id
     * @return 版本列表
     */
    public List<AppVersionDO> queryByAppIdExcludeVersion(Long appId, Long versionId) {
        return appVersionDAO.findByAppIdAndIdNotLike(appId, versionId);
    }

    /**
     * 根据app id查询版本
     * @param appId app id
     * @return 版本列表
     */
    public List<AppVersionDO> queryEnableListByAppId(Long appId) {
        return appVersionDAO.findByAppIdAndEnableOrderByBuildDesc(appId, true);
    }

    /**
     * 是否启用版本
     * @param versionId 版本id
     * @param isEnable
     */
    public void switchAppVersion(Long versionId, boolean isEnable) {
        appVersionDAO.switchVersion(versionId, isEnable);
    }

    /**
     * 删除版本
     * @param versionId 版本id
     */
    public void deleteVersion(Long versionId) {
        appVersionDAO.deleteById(versionId);
    }

    /**
     * 创建版本
     * @param appVersionDO 版本信息
     * @return 版本信息
     */
    public AppVersionDO createVersion(AppVersionDO appVersionDO) {
        return appVersionDAO.saveAndFlush(appVersionDO);
    }

    /**
     * 根据版本号获取最新版本关联实体信息
     * @param versionId 版本号
     * @return 关联实体信息
     */
    public AppJoinVersionDO findJoinVersion(Long versionId) {
        return appJoinVersionDAO.findByVersionId(versionId);
    }

    /**
     * 根据应用id获取最新版本实体信息
     * @param appId 应用id
     * @return 关联实体信息
     */
    public AppJoinVersionDO findJoinVersionByAppId(Long appId) {
        Optional<AppJoinVersionDO> optionalAppJoinVersionDO = appJoinVersionDAO.findById(appId);
        if(optionalAppJoinVersionDO.isPresent()) {
            return optionalAppJoinVersionDO.get();
        }
        return null;
    }


    /**
     * 保存（更新）最新版本关联关系
     * @param appJoinVersionDO 最新版本关系
     * @return 最新版本关系
     */
    public AppJoinVersionDO saveJoinVersion(AppJoinVersionDO appJoinVersionDO) {
        return appJoinVersionDAO.saveAndFlush(appJoinVersionDO);
    }

    /**
     * 删除关联关系
     * @param appId
     */
    public void deleteJoinVersion(long appId) {
        appJoinVersionDAO.deleteById(appId);
    }
}