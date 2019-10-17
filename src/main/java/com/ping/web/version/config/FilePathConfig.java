package com.ping.web.version.config;

import com.ping.web.version.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * Title: 文件路径映射
 *
 * @author 二师兄
 * Create Time: 2019/9/20
 */
@Configuration
public class FilePathConfig extends WebMvcConfigurationSupport {

    @Value("${spring.servlet.multipart.location}")
    private String localTempPath;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(Constants.FILE_MAP)
                .addResourceLocations("file:" + localTempPath + File.separator);
        super.addResourceHandlers(registry);

    }
}