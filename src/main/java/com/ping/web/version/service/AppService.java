package com.ping.web.version.service;

import com.ping.web.version.dao.AppDAO;
import com.ping.web.version.dto.AppDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppDAO appDAO;


    /**
     * 查询所有app信息
     * @return app列表
     */
    public List<AppDO> queryAllApp() {
        return appDAO.findAll();
    }

    /**
     * 根据名称和平台查询app列表
     * @param platform 平台 0 Android 1 iOS
     * @return
     */
    public List<AppDO> findByNameAndPlatform(Integer platform) {
        if(null == platform) {
            return queryAllApp();
        }
        return appDAO.findByPlatform(platform);
    }

    /**
     * 根据Token获取应用信息
     * @param token token信息
     * @return 应用信息
     */
    public AppDO findApp(String token) {
        return appDAO.findByToken(token);
    }

    /**
     * 根据id获取应用信息
     * @param id 应用id
     * @return
     */
    public AppDO findApp(long id) {
        return appDAO.findById(id).get();
    }

    /**
     * 创建App
     * @param appDO app 信息
     */
    public AppDO createApp(AppDO appDO) {
        return appDAO.saveAndFlush(appDO);
    }

    /**
     * 检测是否已经存在指定应用
     * @param packageName 包名
     * @param platform 平台
     * @return
     */
    public boolean isAppExit(String packageName, int platform) {
        List<AppDO> list = appDAO.findByPackageNameAndPlatform(packageName, platform);
        return null != list && list.size() > 0;
    }

    /**
     * 根据包名、平台获取指定应用
     * @param packageName
     * @param platform
     * @return
     */
    public AppDO findExitApp(String packageName, int platform) {
        List<AppDO> list = appDAO.findByPackageNameAndPlatform(packageName, platform);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }
}