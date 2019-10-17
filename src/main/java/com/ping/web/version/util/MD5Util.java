package com.ping.web.version.util;

import com.ping.web.version.dto.AppDO;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Title: MD5工具类
 * Description: 提供MD5相关操作
 *
 * @author 二师兄
 * Create Time: 2019/8/20
 */
public class MD5Util {

    /**
     * 对目标字符串进行MD5操作
     * @param targetStr 待加密字符串
     * @return
     */
    public static String md5Encrypt(String targetStr) {
        if (StringUtils.isEmpty(targetStr)) {
            throw new IllegalArgumentException("String to encrypt was empty");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(targetStr.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }


    /**
     * 生成app token信息
     * @param appDO app信息
     * @return token值
     */
    public static String buildAppTokenValue(AppDO appDO) {
        if(!StringUtils.isEmpty(appDO.getToken())) {
            return appDO.getToken();
        }

        return md5Encrypt(appDO.getName() + appDO.getPackageName() + appDO.getPlatform()).toUpperCase();
    }
}
