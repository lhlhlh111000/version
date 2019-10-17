package com.ping.web.version.app.test;

import com.alibaba.fastjson.JSON;
import com.ping.web.version.app.model.AppInfo;
import com.ping.web.version.app.model.IPAInfo;
import com.ping.web.version.app.model.IPAReader;
import com.ping.web.version.app.service.AppParser;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;

import java.io.File;

public class ParserTests {

    public void testAPK() {

        try (ApkFile apkFile = new ApkFile(new File("/Users/cqt/Downloads/apk/xsz_932919.apk"))) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            System.out.println(apkMeta.getLabel());
            System.out.println(apkMeta.getPackageName());
            System.out.println(apkMeta.getVersionCode());
            System.out.println(apkMeta.getVersionName());
            System.out.println(JSON.toJSONString(apkMeta));
            for (UseFeature feature : apkMeta.getUsesFeatures()) {
                System.out.println(feature.getName());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testIPA() throws Exception{
        String path = "/Users/cqt/Downloads/TQMapAbility.ipa";

        IPAReader reader = new IPAReader(path);
        IPAInfo info = reader.parse();

        System.out.println(JSON.toJSONString(info));

        System.out.println(info.getBundleName());
        System.out.println(info.getRequiredDeviceCapabilities());

    }

    public void testPlist() throws Exception {
        String path = "/Users/cqt/Downloads/TQMapAbility.ipa";

        AppInfo appInfo = AppParser.parse(path);
        System.out.println(JSON.toJSONString(appInfo));

//        FileOutputStream out = new FileOutputStream(new File("/Users/libo/Desktop/test.plist"));
//        PlistGenerator.generatorPlist(appInfo,"http://download.taobaocdn.com/freedom/57894/apple/FlyFish-Release453.ipa", out);
    }

    public void testParser() throws Exception{
        AppInfo appInfo = AppParser.parse("/Users/cqt/Downloads/apk/xsz_932919.apk");
        System.out.println(JSON.toJSONString(appInfo));
    }

    public void testExe() throws Exception{
        AppInfo appInfo = AppParser.parse("/Users/libo/Desktop/1.exe");
        System.out.println(JSON.toJSONString(appInfo));
    }
}
