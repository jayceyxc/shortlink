package com.bcdata.shortlink.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * short link web config
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 21 18:36
 */

@Component
@PropertySource (value = "classpath:config.properties")
@ConfigurationProperties(prefix = "shortlink")
public class ShortLinkWebConfig {
    private String domain;
    private List<String> user = new ArrayList<> ();

    public String getDomain () {
        return domain;
    }

    public void setDomain (String domain) {
        this.domain = domain;
    }

    public List<String> getUser () {
        return user;
    }

    public void setUser (List<String> user) {
        this.user = user;
    }
}
