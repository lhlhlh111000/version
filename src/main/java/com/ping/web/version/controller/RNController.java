package com.ping.web.version.controller;

import com.ping.web.version.constant.Constants;
import com.ping.web.version.dto.CommonResultDO;
import com.ping.web.version.dto.RNVersionDO;
import com.ping.web.version.service.RNVersionService;
import com.ping.web.version.util.HelperUtil;
import com.ping.web.version.util.Sha1CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Title: React Native bundle包管理相关
 *
 * @author 二师兄
 * Create Time: 2019/9/29
 */
@RestController
@RequestMapping("/rn/manager/")
public class RNController {

    @Autowired
    RNVersionService rnVersionService;

    @Value("${spring.servlet.multipart.location}")
    private String localTempPath;

    /**
     * 获取对应版本的rn版本列表
     * @param versionId 版本id
     * @return
     */
    @GetMapping(value = "rn-version-list")
    public CommonResultDO findRNVersionList(long versionId) {
        return new CommonResultDO().success(rnVersionService.queryRNVersionListByVersionId(versionId));
    }

    /**
     * 上传rn版本文件
     * @param file zip压缩包
     * @return 上传结果
     */
    @PostMapping(value = "rn-version-upload")
    public CommonResultDO uploadRNFile(@RequestParam("file") MultipartFile file) {
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

        // 获取bundle文件sha1值
        String sha1 = null;
        String[] bundleFiles = new String[] { // 可设置匹配多文件；含iOS、Android下不通bundle文件名
            "assets/index.android.bundle"
        };
        for(String bundleFile : bundleFiles) {
            try {
                sha1 = Sha1CodeUtil.readZipFileSha1Code(localFilePath, bundleFile);
            }catch (IOException e) {
                e.printStackTrace();
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(sha1)) {
                break;
            }
        }
        if(StringUtils.isEmpty(sha1)) {
            return new CommonResultDO().validateFailed("解析bundle文件SHA1值错误！");
        }

        RNVersionDO rnVersionDO = new RNVersionDO();
        rnVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        rnVersionDO.setSha1(sha1);
        rnVersionDO.setSize(HelperUtil.getDataSize(file.getSize()));
        rnVersionDO.setUrl(Constants.FILE_URL + File.separator + localFileName);
        return new CommonResultDO().success(rnVersionDO);
    }

    /**
     * 创建rn版本
     * @param rnVersionDO 版本信息
     * @return 创建结果
     */
    @PostMapping(value = "create-rn-version")
    public CommonResultDO createRNVersion(@RequestBody RNVersionDO rnVersionDO) {
        return new CommonResultDO().success(rnVersionService.createRNVersion(rnVersionDO));
    }
}
