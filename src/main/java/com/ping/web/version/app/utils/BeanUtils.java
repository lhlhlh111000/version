package com.ping.web.version.app.utils;

import com.ping.web.version.app.model.AppInfo;
import com.ping.web.version.app.model.IPAInfo;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.IOUtils;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BeanUtils {

    public static AppInfo infoFromAPKMeta(ApkMeta meta, byte[] iconData) {
        if (meta == null) {
            return null;
        }
        AppInfo info = new AppInfo();
        info.setVersionCode(meta.getVersionCode());
        info.setVersionName(meta.getVersionName());
        info.setIcon(meta.getIcon());
        info.setPackageName(meta.getPackageName());
        info.setLabel(meta.getLabel());
        info.setFileSize(0);
        info.setMinSdkVersion(meta.getMinSdkVersion());
        info.setIconData(iconData);
        info.setMinSdkString(minLevelString(0,info.getMinSdkVersion()));

        return info;
    }

    public static AppInfo infoFromIPAMeta(IPAInfo meta, String ipaPath) {

        if (meta == null) {
            return null;
        }
        AppInfo info = new AppInfo();
        info.setVersionCode(Long.parseLong(meta.getBundleVersionString().replace(".","")));
        info.setVersionName(meta.getBundleVersionString());
        info.setIcon(meta.getBundleIconFileName());
        info.setPackageName(meta.getBundleIdentifier());
        info.setLabel(meta.getBundleName());
        info.setFileSize(meta.getFileSize());
        info.setMinSdkVersion(meta.getMinimumOSVersion());
        info.setMinSdkString(minLevelString(1,info.getMinSdkVersion()));

        try {

            InputStream pyInput = BeanUtils.class.getClassLoader().getResourceAsStream("ipin.py");

            String pngPath = new File(ipaPath).getParent() + "/icon_tmp.png";

            IOUtils.write(meta.getBundleIcon(), new FileOutputStream(pngPath));

            String[] args = {pngPath};
            PythonInterpreter.initialize(System.getProperties(),System.getProperties(),args);
            PythonInterpreter interpreter = new PythonInterpreter();
            System.out.println("to exchange img data");
            interpreter.execfile(pyInput);

            System.out.println("to set img data");
            info.setIconData(IOUtils.toByteArray(new FileInputStream(pngPath)));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    private static String minLevelString(int type , String minSdk) {

        if (minSdk == null) {
            return "";
        }
        if (type == 0){
            String target = "";
            switch (Integer.parseInt(minSdk)) {
                case 1: target = "1.0"; break;
                case 2: target = "1.1"; break;
                case 3: target = "1.5"; break;
                case 4: target = "1.6"; break;
                case 5: target = "2.0"; break;
                case 6: target = "2.0.1"; break;
                case 7: target = "2.1.x"; break;
                case 8: target = "2.2.x"; break;
                case 9: target = "2.3.0/1/2"; break;
                case 10: target = "2.3.3/4"; break;
                case 11: target = "3.0.x"; break;
                case 12: target = "3.1.x"; break;
                case 13: target = "3.2"; break;
                case 14: target = "4.0.0/1/2"; break;
                case 15: target = "4.0.3/4"; break;
                case 16: target = "4.1"; break;
                case 17: target = "4.2"; break;
                case 18: target = "4.3"; break;
                case 19: target = "4.4"; break;
                case 20: target = "4.4W"; break;
                case 21: target = "5.0"; break;
                case 22: target = "5.1"; break;
                case 23: target = "6.0"; break;
                case 24: target = "7.0"; break;
                case 25: target = "8.0"; break;
            }
            return "Android "+ target;
        }else {
            return "iOS " + minSdk;
        }
    }

}
