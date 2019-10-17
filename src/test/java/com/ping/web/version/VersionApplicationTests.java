package com.ping.web.version;

import com.ping.web.version.dao.AppJoinVersionDAO;
import com.ping.web.version.dto.AppDO;
import com.ping.web.version.dto.AppJoinVersionDO;
import com.ping.web.version.dto.AppVersionDO;
import com.ping.web.version.service.AppService;
import com.ping.web.version.service.AppVersionService;
import com.ping.web.version.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VersionApplicationTests {

    @Autowired
    AppService appService;

    @Autowired
    AppVersionService appVersionService;

    @Autowired
    AppJoinVersionDAO appJoinVersionDAO;

    @Test
    public void contextLoads() {
    }


    @Test
    public void createApp() {
        AppDO appDO = new AppDO();

        appDO.setCreateTime(new Date(System.currentTimeMillis()));
        appDO.setDesc("更新日志：\n1、xxxx \n 2、xxx");
        appDO.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566300732495&di=eb246c3b552f7804e01033347c783d7b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01acc45607d5826ac7251df87e05b8.jpg%401280w_1l_2o_100sh.png");
        appDO.setName("淘汽1");
        appDO.setPackageName("com.taoqicar.mall");
        appDO.setPlatform(0);
        appDO.setToken(MD5Util.buildAppTokenValue(appDO));
        appService.createApp(appDO);

        appDO = new AppDO();
        appDO.setCreateTime(new Date(System.currentTimeMillis()));
        appDO.setDesc("更新日志：\n1、xxxx \n 2、xxx");
        appDO.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566300732495&di=eb246c3b552f7804e01033347c783d7b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01acc45607d5826ac7251df87e05b8.jpg%401280w_1l_2o_100sh.png");
        appDO.setName("淘汽2");
        appDO.setPackageName("com.taoqicar.mall2");
        appDO.setPlatform(0);
        appDO.setToken(MD5Util.buildAppTokenValue(appDO));
        appService.createApp(appDO);

        appDO = new AppDO();
        appDO.setCreateTime(new Date(System.currentTimeMillis()));
        appDO.setDesc("更新日志：\n1、xxxx \n 2、xxx");
        appDO.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566300732495&di=eb246c3b552f7804e01033347c783d7b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01acc45607d5826ac7251df87e05b8.jpg%401280w_1l_2o_100sh.png");
        appDO.setName("淘汽3");
        appDO.setPackageName("com.taoqicar.mall3");
        appDO.setPlatform(0);
        appDO.setToken(MD5Util.buildAppTokenValue(appDO));
        appService.createApp(appDO);
    }


    @Test
    public void createVersion() {
        AppVersionDO  appVersionDO = new AppVersionDO();

        appVersionDO.setAppId(2L);
        appVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        appVersionDO.setUrl("http://10.0.25.51:8090/pages/viewpage.action?pageId=983542");
        appVersionDO.setUpdateTips("更新提示：\n 1、xxxxxx \n 2、xxxxx");
        appVersionDO.setSize("32.36M");
        appVersionDO.setForce(true);
        appVersionDO.setEnable(true);
        appVersionDO.setBuildName("v1.0.0");
        appVersionDO.setBuild(100);
        appVersionService.createVersion(appVersionDO);

        appVersionDO = new AppVersionDO();
        appVersionDO.setAppId(2L);
        appVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        appVersionDO.setUrl("http://10.0.25.51:8090/pages/viewpage.action?pageId=983542");
        appVersionDO.setUpdateTips("更新提示：\n 1、xxxxxx \n 2、xxxxx");
        appVersionDO.setSize("32.36M");
        appVersionDO.setForce(true);
        appVersionDO.setEnable(true);
        appVersionDO.setBuildName("v1.0.1");
        appVersionDO.setBuild(101);
        appVersionService.createVersion(appVersionDO);

        appVersionDO = new AppVersionDO();
        appVersionDO.setAppId(2L);
        appVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        appVersionDO.setUrl("http://10.0.25.51:8090/pages/viewpage.action?pageId=983542");
        appVersionDO.setUpdateTips("更新提示：\n 1、xxxxxx \n 2、xxxxx");
        appVersionDO.setSize("32.36M");
        appVersionDO.setForce(true);
        appVersionDO.setEnable(true);
        appVersionDO.setBuildName("v1.0.2");
        appVersionDO.setBuild(102);
        appVersionService.createVersion(appVersionDO);

        appVersionDO = new AppVersionDO();
        appVersionDO.setAppId(2L);
        appVersionDO.setCreateTime(new Date(System.currentTimeMillis()));
        appVersionDO.setUrl("http://10.0.25.51:8090/pages/viewpage.action?pageId=983542");
        appVersionDO.setUpdateTips("更新提示：\n 1、xxxxxx \n 2、xxxxx");
        appVersionDO.setSize("32.36M");
        appVersionDO.setForce(true);
        appVersionDO.setEnable(true);
        appVersionDO.setBuildName("v1.0.3");
        appVersionDO.setBuild(103);
        appVersionService.createVersion(appVersionDO);
    }

    @Test
    public void joinAppVersion() {
        AppJoinVersionDO appJoinVersionDO  = new AppJoinVersionDO();
        appJoinVersionDO.setAppId(1);
        appJoinVersionDO.setVersionId(4);
        appJoinVersionDAO.save(appJoinVersionDO);
    }

    @Test
    public void subStrTest() {
        String appName = "hello.apk";

        System.out.println(appName.substring(0, appName.indexOf(".")));
        System.out.println(appName.substring(appName.indexOf(".")));
    }

    @Test
    public void buildToken() {
        AppDO appDO = new AppDO();
        appDO.setPackageName("com.mall.taoqicar");
        appDO.setPlatform(0);
        appDO.setName("淘汽");
        String token = MD5Util.buildAppTokenValue(appDO);
        System.out.println(token);
    }
}
