package com.ping.web.version.app.utils;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.ping.web.version.app.model.AppInfo;

import java.io.OutputStream;

public class PlistGenerator {


    /**
     * 生成企业版发布的plist
     */
    public static void generatorPlist(AppInfo appInfo, String ipaUrl, OutputStream out) throws Exception{

        NSDictionary infoDic = new NSDictionary();
        infoDic.put("bundle-identifier", appInfo.getPackageName());
        infoDic.put("bundle-version", appInfo.getVersionName());
        infoDic.put("kind", "software");
        infoDic.put("title", appInfo.getLabel());

        NSArray assetArray = new NSArray(1);
        NSDictionary assetDic = new NSDictionary();
        assetDic.put("kind", "software-package");
        assetDic.put("url", ipaUrl);
        assetArray.setValue(0, assetDic);

        NSDictionary assets = new NSDictionary();
        assets.put("assets", assetArray);
        assets.put("metadata", infoDic);

        NSDictionary rootDic = new NSDictionary();
        rootDic.put("items", new NSArray(assets));

        PropertyListParser.saveAsXML(rootDic, out);
    }

    /**
     * 生成企业版schemal
     */
    public static String generatorSchemal(String plistUrl) {
        return "itms-services://?action=download-manifest&url=" + plistUrl;
    }
}
