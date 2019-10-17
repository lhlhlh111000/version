package com.ping.web.version.controller;

import com.ping.web.version.app.model.AppInfo;
import com.ping.web.version.app.service.AppParser;
import com.ping.web.version.constant.Constants;
import com.ping.web.version.dto.AppDO;
import com.ping.web.version.dto.AppJoinVersionDO;
import com.ping.web.version.dto.AppVersionDO;
import com.ping.web.version.dto.CommonResultDO;
import com.ping.web.version.service.AppService;
import com.ping.web.version.service.AppVersionService;
import com.ping.web.version.util.HelperUtil;
import com.ping.web.version.util.MD5Util;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/version/manager/")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private AppVersionService appVersionService;

    @Value("${spring.servlet.multipart.location}")
    private String localTempPath;


    /**
     * 根据应用名、平台查询app列表
     * @param platform 平台
     * @return app列表
     */
    @RequestMapping(value = "app-list", method = RequestMethod.GET)
    public CommonResultDO findApp(Integer platform) {
        return new CommonResultDO().success(appService.findByNameAndPlatform(platform));
    }

    /**
     * 根据id查询应用信息
     * @param id 应用id
     * @return 应用信息
     */
    @RequestMapping(value = "app-info", method = RequestMethod.GET)
    public CommonResultDO findApp(Long id) {
        return new CommonResultDO().success(appService.findApp(id));
    }

    /**
     * app 创建
     * @param appDO app信息
     * @return 结果
     */
    @RequestMapping(value = "app-create", method = RequestMethod.POST)
    public CommonResultDO createApp(AppDO appDO) {
        boolean isExit = appService.isAppExit(appDO.getPackageName(), appDO.getPlatform());
        if(isExit) {
            return new CommonResultDO().validateFailed("已经存在指定包名的应用！");
        }
        String token = MD5Util.buildAppTokenValue(appDO);
        appDO.setToken(token);
        appDO.setCreateTime(new Date(System.currentTimeMillis()));
        return new CommonResultDO().success(appService.createApp(appDO));
    }

    /**
     * 根据app id 获取版本列表
     * @param appId 应用id
     * @return 版本列表
     */
    @RequestMapping(value = "app-version-list", method = RequestMethod.GET)
    public CommonResultDO findAppVersionList(Long appId) {
        return new CommonResultDO().success(appVersionService.queryByAppId(appId));
    }

    /**
     * 改变版本开启状态
     * @param versionId 版本 id
     * @param isEnable 是否启用
     * @return
     */
    @RequestMapping(value = "app-version-switch", method = RequestMethod.GET)
    public CommonResultDO switchAppVersion(Long versionId, boolean isEnable) {
        appVersionService.switchAppVersion(versionId, isEnable);
        if(isEnable) {
            // 对关联最新版本信息进行更新
            AppVersionDO appVersionDO = appVersionService.queryByVersionId(versionId);
            AppJoinVersionDO appJoinVersionDO = appVersionService.findJoinVersionByAppId(appVersionDO.getAppId());
            if(null == appJoinVersionDO) {
                appJoinVersionDO = new AppJoinVersionDO();
                appJoinVersionDO.setAppId(appVersionDO.getAppId());
            }
            if(maxVersionBuild(appJoinVersionDO.getVersionId(), versionId) == versionId) {
                appJoinVersionDO.setVersionId(versionId);
                appVersionService.saveJoinVersion(appJoinVersionDO);
            }
        }else {
            checkNeedChangeLatestVersion(versionId);
        }
        return new CommonResultDO().success(true);
    }

    /**
     * 删除指定版本
     * @param versionId 版本 id
     * @return
     */
    @RequestMapping(value = "app-version-delete", method = RequestMethod.GET)
    public CommonResultDO deleteVersion(Long versionId) {
        checkNeedChangeLatestVersion(versionId);
        appVersionService.deleteVersion(versionId);
        return new CommonResultDO().success(true);
    }

    /**
     * 检查是否需要变更最新版本
     * @param versionId
     */
    private void checkNeedChangeLatestVersion(Long versionId) {
        AppJoinVersionDO appJoinVersionDO = appVersionService.findJoinVersion(versionId);
        if(null == appJoinVersionDO) {
            return;
        }

        List<AppVersionDO> list = appVersionService.queryByAppIdExcludeVersion(appJoinVersionDO.getAppId(), versionId);
        if(null != list && !list.isEmpty()) {
//            for(AppVersionDO appVersionDO : list) {
//                if(appVersionDO.getId() != versionId && appVersionDO.isEnable()) {
//                    appJoinVersionDO.setVersionId(appVersionDO.getId());
//                    appVersionService.saveJoinVersion(appJoinVersionDO);
//                    return;
//                }
//            }
            appJoinVersionDO.setVersionId(list.get(0).getId());
            appVersionService.saveJoinVersion(appJoinVersionDO);
        }else {
            appVersionService.deleteJoinVersion(appJoinVersionDO.getAppId());
        }
    }

    /**
     * 版本创建
     * @param appDO 应用信息
     * @return 返回结果
     */
    @RequestMapping(value = "app-version-create", method = RequestMethod.POST)
    public CommonResultDO createVersion(@RequestBody AppDO appDO) {
        AppDO exitAppDO = appService.findExitApp(appDO.getPackageName(), appDO.getPlatform());
        if(null == exitAppDO) { // 创建App
            CommonResultDO createResult = createApp(appDO);
            if(!createResult.isSuccess()) {
                return createResult;
            }

            exitAppDO = (AppDO) createResult.getData();
        }

        AppVersionDO appVersionDO = appDO.getLatestVersion();
        appVersionDO.setAppId(exitAppDO.getId());
        appVersionDO = appVersionService.createVersion(appVersionDO);
        // 更新最新版本信息
        // 默认版本是关的，所以去除最新版本关联
//        AppJoinVersionDO appJoinVersionDO = appVersionService.findJoinVersionByAppId(appVersionDO.getAppId());
//        if(null == appJoinVersionDO) {
//            appJoinVersionDO = new AppJoinVersionDO();
//            appJoinVersionDO.setAppId(exitAppDO.getId());
//            appJoinVersionDO.setVersionId(appVersionDO.getId());
//            appVersionService.saveJoinVersion(appJoinVersionDO);
//        }else {
//            appJoinVersionDO.setVersionId(maxVersionBuild(appJoinVersionDO.getVersionId(), appVersionDO.getId()));
//            appVersionService.saveJoinVersion(appJoinVersionDO);
//        }
        return new CommonResultDO().success(appVersionDO);
    }

    /**
     * 比较两个版本build号谁更大
     * @param oldVersionId 一个版本
     * @param newVersionId 另一个版本
     * @return 大版本号
     */
    private Long maxVersionBuild(Long oldVersionId, Long newVersionId) {
        if(0 == oldVersionId) {
            return newVersionId;
        }
        if(appVersionService.queryByVersionId(oldVersionId).getBuild() < appVersionService.queryByVersionId(newVersionId).getBuild()) {
            return newVersionId;
        }else {
            return oldVersionId;
        }
    }

    /**
     * 获取对应应用最新版本信息
     * @param token 应用token信息
     * @return 版本信息
     */
    @RequestMapping(value = "app-version-latest", method = RequestMethod.GET)
    public CommonResultDO latestVersion(String token) {
        AppDO appDO = appService.findApp(token);
        if(null == appDO) {
            return new CommonResultDO().validateFailed("未能找到指定应用信息");
        }

        List<AppVersionDO> list = appVersionService.queryEnableListByAppId(appDO.getId());
        if(null == list || list.size() <= 0) {
            return new CommonResultDO().validateFailed("未能获取指定应用版本信息");
        }

        return new CommonResultDO().success(list.get(0));
    }

    /**
     * 应用文件上传
     * @param file 上传文件
     * @return 结果信息
     */
    @RequestMapping(value = "app-version-upload", method = RequestMethod.POST)
    public CommonResultDO uploadAppFile(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return new CommonResultDO().validateFailed("文件内容为空！");
        }

        // 本地文件存储
        String fileName = file.getOriginalFilename();
        String rawFileName = fileName.substring(0, fileName.indexOf("."));
        String fileType = fileName.substring(fileName.indexOf("."));
        String localFileName = rawFileName + "-" + System.currentTimeMillis() + fileType;
        String localFilePath = localTempPath + File.separator + localFileName;
        try {
            file.transferTo(new File(localFilePath));
        }catch (IOException e) {
            e.printStackTrace();
            return new CommonResultDO().validateFailed(e.getMessage());
        }

        // app信息解析
        AppInfo appInfo;
        try {
            appInfo = AppParser.parse(localFilePath);
            appInfo.setFileSize(file.getSize());
        }catch (Exception e) {
            e.printStackTrace();
            return new CommonResultDO().validateFailed(e.getMessage());
        }
        // icon图片生成
        appInfo.setIcon("");
        if(null != appInfo.getIconData()) {
            try {
                String localIconName = rawFileName + "-" + System.currentTimeMillis() + ".png";
                String localIconPath = localTempPath + File.separator + localIconName;
                IOUtils.write(appInfo.getIconData(), new FileOutputStream(new File(localIconPath)));
                appInfo.setIcon(Constants.FILE_URL + File.separator + localIconName);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 生成app信息
        AppDO appDO = buildAppDO(appInfo, Constants.FILE_URL + File.separator + localFileName);
        return new CommonResultDO().success(appDO);
    }

    /**
     * 构建app信息
     * @param appInfo 解析出的应用信息
     * @param appPath 应用下载地址
     * @return
     */
    private AppDO buildAppDO(AppInfo appInfo, String appPath) {
        AppDO appDO = new AppDO();
        appDO.setPackageName(appInfo.getPackageName());
        appDO.setName(appInfo.getLabel());
        appDO.setCreateTime(new Date(System.currentTimeMillis()));
        appDO.setPlatform(appPath.endsWith(".apk") ? 0 : 1);
        appDO.setToken(MD5Util.buildAppTokenValue(appDO));
        appDO.setIcon(appInfo.getIcon());

        AppVersionDO appVersionDO = new AppVersionDO();
        appVersionDO.setBuild(Integer.parseInt(appInfo.getVersionCode() + ""));
        appVersionDO.setBuildName(appInfo.getVersionName());
        appVersionDO.setEnable(false);
        appVersionDO.setForce(false);
        appVersionDO.setSize(HelperUtil.getDataSize(appInfo.getFileSize()));
        appVersionDO.setUrl(appPath);
        appVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        appDO.setLatestVersion(appVersionDO);

        return appDO;
    }

}