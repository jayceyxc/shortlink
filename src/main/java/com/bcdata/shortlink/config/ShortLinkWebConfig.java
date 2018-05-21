package com.bcdata.shortlink.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * short link web config
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 21 18:36
 */

@Component
@ConfigurationProperties(prefix = "shortlink")
public class ShortLinkWebConfig {
    private String domain;

    public String getDomain () {
        return domain;
    }

    public void setDomain (String domain) {
        this.domain = domain;
    }
}
