package com.zgczx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Jason
 * @Author: Administrator
 * @Date: 2019/1/2 14:29
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {
    /**
     * 微信公众平台授权url
     */
    public String weChatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    public String weChatOpenAuthorize;

    /**
     * 学生发展平台
     */
    public String sdc;

    /**
     * 主页地址
     */
    public String homeAddress;

}
